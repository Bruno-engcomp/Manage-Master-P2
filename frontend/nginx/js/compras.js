/**
 * ARQUIVO: js/compras.js
 * Lógica: Registro de Vendas com preço manual e referência de preço original.
 */
document.addEventListener("DOMContentLoaded", () => {
    
    let carrinho = []; 
    let precoOriginalDoBanco = 0; // Variável de controlo

    // Seletores do HTML
    const inputProduto = document.getElementById('produto-nome');
    const inputQtd = document.getElementById('produto-qtd');
    const inputPreco = document.getElementById('produto-preco');
    const precoRefElement = document.getElementById('preco-unitario-ref'); // O <small> que criámos
    
    const btnAdicionar = document.getElementById('btn-adicionar');
    const btnFinalizar = document.getElementById('btn-finalizar');
    const tabelaBody = document.getElementById('tabela-itens');
    const totalDisplay = document.getElementById('total-geral');
    const selectPagamento = document.getElementById('forma-pagamento');

    // --- 1. BUSCAR PRODUTO (Ao sair do campo de nome/ID) ---
    inputProduto.addEventListener('blur', async () => {
        const valor = inputProduto.value.trim();
        if (!valor) return;

        try {
            let url = !isNaN(valor) 
                ? `http://localhost:8080/api/produtos/${valor}` 
                : `http://localhost:8080/api/produtos/buscar?nome=${encodeURIComponent(valor)}`;

            const response = await fetch(url);
            
            if (response.ok) {
                const produto = await response.json();
                
                // Preenche dados ocultos no dataset
                inputProduto.value = produto.nome;
                inputProduto.dataset.idReal = produto.id;
                
                // IMPORTANTE: Captura o fornecedor do produto para o Java não dar erro de "null"
                inputProduto.dataset.fornecedorId = produto.fornecedorId || (produto.fornecedor ? produto.fornecedor.id : 1);

                // Lógica de Preços
                precoOriginalDoBanco = parseFloat(produto.preco);
                inputPreco.value = precoOriginalDoBanco.toFixed(2);
                
                // Esconde a legenda sempre que um novo produto é carregado
                if (precoRefElement) {
                    precoRefElement.style.display = 'none';
                    precoRefElement.innerText = `Preço Unitário original: R$ ${precoOriginalDoBanco.toFixed(2)}`;
                }

                inputPreco.focus();
                inputPreco.select(); 

            } else {
                console.warn("Produto não encontrado");
                inputProduto.dataset.idReal = "";
            }
        } catch (error) {
            console.error("Erro na busca:", error);
        }
    });

    // --- 2. MONITORAR ALTERAÇÃO DE PREÇO (Sua solicitação) ---
    inputPreco.addEventListener('input', () => {
        const precoDigitado = parseFloat(inputPreco.value);

        if (precoRefElement) {
            // Só aparece se o preço for diferente do banco e não estiver vazio
            if (!isNaN(precoDigitado) && precoDigitado !== precoOriginalDoBanco) {
                precoRefElement.style.display = 'block';
            } else {
                precoRefElement.style.display = 'none';
            }
        }
    });

    // --- 3. ADICIONAR ITEM AO CARRINHO ---
    btnAdicionar.addEventListener("click", () => {
        const idReal = inputProduto.dataset.idReal;
        const fornecedorId = inputProduto.dataset.fornecedorId;
        const nome = inputProduto.value.trim();
        const qtd = parseInt(inputQtd.value);
        const precoVendaManual = parseFloat(inputPreco.value);

        if (!idReal) return alert("Pesquise um produto primeiro.");
        if (isNaN(qtd) || qtd <= 0) return alert("Quantidade inválida.");
        if (isNaN(precoVendaManual) || precoVendaManual < 0) return alert("Preço inválido.");

        const item = {
            produtoId: parseInt(idReal),
            fornecedorId: parseInt(fornecedorId), // Necessário para o Backend
            nome: nome,
            quantidade: qtd,
            preco: precoVendaManual,
            subtotal: qtd * precoVendaManual
        };

        carrinho.push(item);
        atualizarTabela();
        limparCampos();
    });

    // --- 4. ATUALIZAR TABELA E TOTAL ---
    function atualizarTabela() {
        tabelaBody.innerHTML = ""; 
        let totalGeral = 0;

        if (carrinho.length === 0) {
            tabelaBody.innerHTML = '<tr><td colspan="4" class="empty-msg">Nenhum item adicionado.</td></tr>';
            totalDisplay.innerText = "R$ 0,00";
            return;
        }

        carrinho.forEach((item, index) => {
            totalGeral += item.subtotal;
            const tr = document.createElement("tr");
            tr.innerHTML = `
                <td>${item.nome}</td>
                <td style="text-align: center;">${item.quantidade}</td>
                <td>R$ ${item.subtotal.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}</td>
                <td style="text-align: center;">
                    <button type="button" onclick="removerItem(${index})" style="color:red; border:none; background:none; cursor:pointer;">
                        <i class="fas fa-trash"></i>
                    </button>
                </td>
            `;
            tabelaBody.appendChild(tr);
        });

        totalDisplay.innerText = "R$ " + totalGeral.toLocaleString('pt-BR', { minimumFractionDigits: 2 });
    }

    window.removerItem = function(index) {
        carrinho.splice(index, 1);
        atualizarTabela();
    };

    function limparCampos() {
        inputProduto.value = "";
        inputQtd.value = "1";
        inputPreco.value = "";
        inputProduto.dataset.idReal = "";
        if (precoRefElement) precoRefElement.style.display = 'none';
        inputProduto.focus();
    }

    // --- 5. FINALIZAR VENDA (Envia o JSON para o Java) ---
    btnFinalizar.addEventListener("click", async () => {
        if (carrinho.length === 0) return alert("Adicione itens ao carrinho!");
        
        if (!confirm(`Finalizar venda de ${totalDisplay.innerText}?`)) return;

        // Construção do Payload exatamente como o seu VendaCreateDTO espera
        const vendaPayload = {
            fornecedorId: carrinho[0].fornecedorId, // Pega o fornecedor do primeiro item
            formaPagamento: selectPagamento.value, 
            numeroDocumento: "VEN-" + Date.now(),
            itens: carrinho.map(item => ({
                produtoId: item.produtoId,
                quantidade: item.quantidade,
                preco: item.preco
            }))
        };

        try {
            const response = await fetch('http://localhost:8080/api/vendas', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(vendaPayload)
            });

            if (response.ok) {
                alert("✅ Venda realizada e stock atualizado!");
                carrinho = [];
                atualizarTabela();
            } else {
                const errorData = await response.json();
                alert("❌ Erro no Servidor: " + (errorData.message || "Verifique os dados."));
            }
        } catch (error) {
            alert("Erro de conexão com o servidor. O Java está rodando?");
        }
    });
});