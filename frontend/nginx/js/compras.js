document.addEventListener("DOMContentLoaded", () => {
    let carrinho = []; 

    const inputProduto = document.getElementById('produto-nome');
    const inputQtd = document.getElementById('produto-qtd');
    const inputPreco = document.getElementById('produto-preco');
    const btnAdicionar = document.getElementById('btn-adicionar');
    const btnFinalizar = document.getElementById('btn-finalizar');
    const tabelaBody = document.getElementById('tabela-itens');
    const totalDisplay = document.getElementById('total-geral');
    const selectPagamento = document.getElementById('forma-pagamento');

    // --- 1. BUSCAR PRODUTO (Capturando Fornecedor) ---
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
                inputProduto.value = produto.nome;
                inputProduto.dataset.idReal = produto.id;
                
                // IMPORTANTE: Guardamos o fornecedor que veio do produto
                // O nome do campo depende do seu ProdutoResponseDTO (nomeFornecedor ou fornecedorId)
                // Se o seu DTO de resposta não envia o ID, você precisará ajustar o DTO.
                inputProduto.dataset.fornecedorId = produto.fornecedorId || 1; 

                inputPreco.value = produto.preco;
                inputPreco.focus();
                inputPreco.select();
            }
        } catch (error) { console.error("Erro na busca:", error); }
    });

    // --- 2. ADICIONAR ITEM ---
    btnAdicionar.addEventListener("click", () => {
        const idReal = inputProduto.dataset.idReal;
        const fornecedorId = inputProduto.dataset.fornecedorId;
        const nome = inputProduto.value.trim();
        const qtd = parseInt(inputQtd.value);
        const preco = parseFloat(inputPreco.value);

        if (!idReal || isNaN(qtd) || isNaN(preco)) return alert("Preencha os dados corretamente.");

        carrinho.push({
            produtoId: parseInt(idReal),
            fornecedorId: parseInt(fornecedorId),
            nome,
            quantidade: qtd,
            preco,
            subtotal: qtd * preco
        });

        atualizarTabela();
        limparCampos();
    });

    // --- 3. FINALIZAR VENDA (Enviando fornecedorId) ---
    btnFinalizar.addEventListener("click", async () => {
        if (carrinho.length === 0) return alert("Carrinho vazio!");

        const vendaPayload = {
            // Pegamos o fornecedor do primeiro item para o cabeçalho
            fornecedorId: carrinho[0].fornecedorId, 
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
                alert("✅ Venda realizada com sucesso!");
                carrinho = [];
                atualizarTabela();
            } else {
                const erro = await response.json();
                alert("❌ Erro: " + (erro.message || "Falha na validação"));
            }
        } catch (error) { alert("Erro de conexão."); }
    });

    function atualizarTabela() {
        tabelaBody.innerHTML = "";
        let total = 0;
        carrinho.forEach((item, i) => {
            total += item.subtotal;
            tabelaBody.innerHTML += `<tr><td>${item.nome}</td><td>${item.quantidade}</td><td>R$ ${item.subtotal.toFixed(2)}</td></tr>`;
        });
        totalDisplay.innerText = "R$ " + total.toFixed(2);
    }

    function limparCampos() {
        inputProduto.value = "";
        inputPreco.value = "";
        inputProduto.dataset.idReal = "";
        inputProduto.focus();
    }
});