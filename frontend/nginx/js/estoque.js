// Variável global para armazenar o ID do produto que será excluído
let produtoIdParaExcluir = null;

/**
 * Busca os produtos do backend e preenche a tabela
 */
async function carregarEstoque() {
    try {
        // Chamada para o seu endpoint de produtos
        const response = await fetch('http://localhost:8080/api/produtos');
        
        if (!response.ok) {
            throw new Error('Não foi possível carregar os dados do servidor.');
        }

        const produtos = await response.json();
        const tbody = document.getElementById('corpoTabela');
        
        // Limpa a tabela antes de preencher
        tbody.innerHTML = ""; 

        if (produtos.length === 0) {
            tbody.innerHTML = '<tr><td colspan="7" style="text-align:center;">Nenhum produto encontrado.</td></tr>';
            return;
        }

        produtos.forEach(produto => {
            // Cálculo do valor total da linha
            const totalLinha = produto.preco * produto.quantidade;
            
            // Define a cor do badge baseado no status 'vencido' do seu DTO
            const badgeVencido = produto.vencido ? 'bg-danger' : 'bg-success';
            const textoVencido = produto.vencido ? 'Vencido' : 'Ok';

            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${produto.nome}</td>
                <td><span class="badge bg-roupa">${produto.nomeCategoria || 'Geral'}</span></td>
                <td>${produto.nomeFornecedor || 'Sem Fornecedor'}</td>
                <td class="col-preco">R$ ${produto.preco.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}</td>
                <td class="col-qtd">${produto.quantidade}</td>
                <td class="col-total">R$ ${totalLinha.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}</td>
                <td>
                    <button class="btn-delete" onclick="abrirModalExclusao(${produto.id})" title="Remover item">
                        <i class="fa-solid fa-trash"></i>
                    </button>
                </td>
            `;
            tbody.appendChild(tr);
        });

    } catch (error) {
        console.error("Erro ao carregar estoque:", error);
        const tbody = document.getElementById('corpoTabela');
        tbody.innerHTML = '<tr><td colspan="7" style="color: red; text-align:center;">Erro ao conectar com o servidor.</td></tr>';
    }
}

/**
 * Abre o modal de confirmação e guarda o ID do produto
 */
function abrirModalExclusao(id) {
    produtoIdParaExcluir = id;
    const modal = document.getElementById('modalConfirmacao');
    modal.style.display = 'flex';
}

/**
 * Fecha o modal e limpa o ID selecionado
 */
function fecharModal() {
    const modal = document.getElementById('modalConfirmacao');
    modal.style.display = 'none';
    produtoIdParaExcluir = null;
}

/**
 * Executa a exclusão real no Backend via DELETE
 */
async function confirmarExclusao() {
    if (!produtoIdParaExcluir) return;

    try {
        const response = await fetch(`http://localhost:8080/api/produtos/${produtoIdParaExcluir}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            fecharModal();
            alert("Produto removido com sucesso!");
            carregarEstoque(); // Atualiza a lista após deletar
        } else {
            const erro = await response.text();
            alert("Erro ao remover: " + erro);
        }
    } catch (error) {
        console.error("Erro na requisição de exclusão:", error);
        alert("Erro de conexão ao tentar excluir.");
    }
}

// Inicia o carregamento assim que a página termina de carregar o HTML
document.addEventListener('DOMContentLoaded', carregarEstoque);