/**
 * ARQUIVO: js/compras.js
 * Responsável por gerenciar a lista de vendas e enviar para o backend.
 */
document.addEventListener("DOMContentLoaded", () => {
    
    // --- VARIÁVEIS GLOBAIS ---
    let carrinho = []; // Array para guardar os itens temporariamente
    
    // --- SELETORES (Baseado no seu HTML) ---
    // Como os inputs não têm ID, pegamos pela hierarquia ou atributos
    const inputNome = document.querySelector('.venda-form input[type="text"]');
    const inputQtd = document.querySelector('.venda-form input[min="1"]');
    const inputPreco = document.querySelector('.venda-form input[step="0.01"]');
    
    const btnAdicionar = document.querySelector('.venda-form button'); // Botão "Adicionar à Lista"
    const btnFinalizar = document.querySelector('.btn-finalizar');     // Botão "Finalizar"
    const tabelaBody = document.querySelector('.sales-table tbody');
    const totalDisplay = document.querySelector('.total-bar strong');

    // --- 1. ADICIONAR ITEM AO CARRINHO ---
    btnAdicionar.addEventListener("click", () => {
        const nome = inputNome.value.trim();
        const qtd = parseInt(inputQtd.value);
        const preco = parseFloat(inputPreco.value);

        // Validação simples
        if (!nome || qtd <= 0 || isNaN(preco) || preco <= 0) {
            alert("Por favor, preencha todos os campos corretamente.");
            return;
        }

        const subtotal = qtd * preco;

        // Cria o objeto do item
        const item = {
            nome: nome,
            quantidade: qtd,
            precoUnitario: preco,
            subtotal: subtotal
        };

        // Adiciona ao array e atualiza a tela
        carrinho.push(item);
        atualizarTabela();
        limparFormulario();
    });

    // --- 2. ATUALIZAR A TABELA VISUAL ---
    function atualizarTabela() {
        tabelaBody.innerHTML = ""; // Limpa a tabela atual

        if (carrinho.length === 0) {
            tabelaBody.innerHTML = '<tr><td colspan="4" class="empty-msg">Nenhum item adicionado.</td></tr>';
            totalDisplay.innerText = "R$ 0,00";
            return;
        }

        let totalGeral = 0;

        carrinho.forEach((item, index) => {
            totalGeral += item.subtotal;

            const tr = document.createElement("tr");
            tr.innerHTML = `
                <td>${item.nome}</td>
                <td style="text-align: center;">${item.quantidade}</td>
                <td>R$ ${item.subtotal.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}</td>
                <td style="text-align: center;">
                    <button class="btn-remove" onclick="removerItem(${index})" style="background:none; border:none; color: #ef4444; cursor:pointer;">
                        <i class="fas fa-trash"></i>
                    </button>
                </td>
            `;
            tabelaBody.appendChild(tr);
        });

        // Atualiza o Total Geral lá embaixo
        totalDisplay.innerText = "R$ " + totalGeral.toLocaleString('pt-BR', { minimumFractionDigits: 2 });
    }

    // --- 3. REMOVER ITEM (Função Global para o onclick funcionar) ---
    window.removerItem = function(index) {
        carrinho.splice(index, 1); // Remove o item do array
        atualizarTabela();         // Redesenha a tabela
    };

    function limparFormulario() {
        inputNome.value = "";
        inputQtd.value = "1";
        inputPreco.value = "";
        inputNome.focus(); // Volta o cursor para o nome
    }

    // --- 4. FINALIZAR VENDA (ENVIAR PARA O BANCO) ---
    btnFinalizar.addEventListener("click", async () => {
        if (carrinho.length === 0) {
            alert("O carrinho está vazio!");
            return;
        }

        // Confirmação
        if (!confirm("Confirmar a venda e dar baixa no estoque?")) return;

        // Prepara os dados para o formato que seu Java (DTO) espera
        // Exemplo de estrutura: { itens: [ { nomeProduto, quantidade, valor }, ... ] }
        const vendaPayload = {
            dataVenda: new Date().toISOString(),
            itens: carrinho.map(item => ({
                nomeProduto: item.nome, // O Backend vai ter que buscar o ID pelo nome ou vc digita o ID
                quantidade: item.quantidade,
                precoVenda: item.precoUnitario
            }))
        };

        try {
            // AJUSTE A URL ABAIXO PARA O SEU ENDPOINT REAL
            const response = await fetch('http://localhost:8080/api/vendas', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(vendaPayload)
            });

            if (response.ok) {
                alert("Venda registrada com sucesso!");
                carrinho = []; // Limpa o carrinho
                atualizarTabela();
            } else {
                const erro = await response.text();
                alert("Erro ao registrar venda: " + erro);
            }
        } catch (error) {
            console.error(error);
            alert("Erro de conexão com o servidor.");
        }
    });
});
