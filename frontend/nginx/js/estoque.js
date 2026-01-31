async function carregarEstoque() {
    try {
        // Faz a chamada para o endpoint que você enviou
        const response = await fetch('http://localhost:8080/produtos');
        
        if (!response.ok) {
            throw new Error('Erro ao buscar dados do servidor');
        }
        
        const produtos = await response.json();
        const tbody = document.getElementById('tabela-produtos');
        tbody.innerHTML = ""; // Limpa a tabela antes de preencher

        produtos.forEach(produto => {
            // Cálculo do total (preço * quantidade)
            const totalVal = produto.preco * produto.quantidade;
            
            // Lógica para cor do badge (ajuste conforme os nomes das suas categorias)
            const catNome = produto.categoriaNome || 'Geral';
            const classeBadge = catNome.toLowerCase().includes('eletr') ? 'bg-eletronico' : 'bg-roupa';

            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${produto.nome}</td>
                <td><span class="badge ${classeBadge}">${catNome}</span></td>
                <td>${produto.fornecedorNome || 'Sem Fornecedor'}</td>
                <td class="col-preco">R$ ${produto.preco.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}</td>
                <td class="col-qtd">${produto.quantidade}</td>
                <td class="col-total">R$ ${totalVal.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}</td>
            `;
            tbody.appendChild(tr);
        });

    } catch (error) {
        console.error("Erro detalhado:", error);
        alert("Erro ao carregar o estoque. Certifique-se que o backend está rodando em http://localhost:8080");
    }
}

// Inicia a função ao carregar a página
document.addEventListener('DOMContentLoaded', carregarEstoque);