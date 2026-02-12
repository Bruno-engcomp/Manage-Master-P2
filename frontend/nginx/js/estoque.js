/**
 * ARQUIVO: ../js/estoque.js
 */

let produtoIdParaExcluir = null;

// 1. LISTAR PRODUTOS (GET)
async function carregarEstoque() {
    const tbody = document.getElementById('corpoTabela');
    try {
        const response = await fetch('http://localhost:8080/api/produtos');
        if (!response.ok) throw new Error("Erro ao buscar dados");

        const produtos = await response.json();
        tbody.innerHTML = ""; 

        if (produtos.length === 0) {
            tbody.innerHTML = '<tr><td colspan="8" style="text-align:center;">Estoque vazio.</td></tr>';
            return;
        }

        produtos.forEach(produto => {
            const preco = Number(produto.preco) || 0;
            const qtd = produto.quantidade || 0;
            const limit = produto.limit || 0; // Pega o limite vindo do Java
            const total = preco * qtd;
            
            const tr = document.createElement('tr');
            
            // Dica: Se a quantidade for menor ou igual ao limite, adiciona um estilo de alerta
            if (qtd <= limit) {
                tr.style.backgroundColor = "rgba(239, 68, 68, 0.1)"; // Fundo levemente avermelhado
                tr.title = "Atenção: Estoque baixo!";
            }

            tr.innerHTML = `
                <td><strong>#${produto.id}</strong></td>
                <td>${produto.nome}</td>
                <td><span class="badge bg-roupa">${produto.nomeCategoria || 'Geral'}</span></td>
                <td>${produto.nomeFornecedor || 'Sem Fornecedor'}</td>
                <td>R$ ${preco.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}</td>
                <td>${qtd}</td>
                <td>R$ ${total.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}</td>
                <td class="actions-cell">
                    <button class="btn-action btn-edit" onclick='prepararEdicao(${JSON.stringify(produto)})'>
                        <i class="fa-solid fa-pen-to-square"></i>
                    </button>
                    <button class="btn-action btn-delete" onclick="abrirModalExclusao(${produto.id})">
                        <i class="fa-solid fa-trash"></i>
                    </button>
                </td>
            `;
            tbody.appendChild(tr);
        });
    } catch (error) {
        tbody.innerHTML = '<tr><td colspan="8" style="color:red; text-align:center;">Erro de conexão com o servidor.</td></tr>';
    }
}

// 2. EXCLUIR PRODUTO (DELETE)
function abrirModalExclusao(id) {
    produtoIdParaExcluir = id;
    document.getElementById('modalConfirmacao').style.display = 'flex';
}

function fecharModal() {
    document.getElementById('modalConfirmacao').style.display = 'none';
}

async function confirmarExclusao() {
    try {
        const response = await fetch(`http://localhost:8080/api/produtos/${produtoIdParaExcluir}`, { method: 'DELETE' });
        if (response.ok) {
            fecharModal();
            carregarEstoque();
        }
    } catch (e) { alert("Erro ao excluir."); }
}

// 3. EDITAR PRODUTO (PUT)
// ... (resto do código)

// 3. EDITAR PRODUTO (PUT)
function prepararEdicao(produto) {
    // 1. Preenche os campos básicos
    document.getElementById("editIndex").value = produto.id;
    document.getElementById("editNome").value = produto.nome;
    
    // 2. Formatação de preço
    const precoFormatado = Number(produto.preco).toLocaleString('pt-BR', { minimumFractionDigits: 2 });
    document.getElementById("editPreco").value = precoFormatado;
    
    // 3. Quantidade Atual
    document.getElementById("editQtd").value = produto.quantidade;

    // 4. LIMIT (Sincronização com o campo de perigo do cadastro)
    // Se o produto não tiver limite definido (null ou undefined), coloca 0
    document.getElementById("editLimit").value = produto.limit !== undefined ? produto.limit : 0; 

    // 5. Validade
    document.getElementById("editValidade").value = produto.validade || "";
    
    // Abre o modal
    document.getElementById("modalEdicao").style.display = "flex";
}

// ... (resto do código)

function fecharModalEdicao() {
    document.getElementById("modalEdicao").style.display = "none";
}

async function salvarEdicao(event) {
    event.preventDefault();

    const id = document.getElementById("editIndex").value;
    const precoBr = document.getElementById("editPreco").value;
    
    // Converte "1.500,50" para 1500.50
    const precoLimpo = precoBr.replace(/\./g, '').replace(',', '.');
    
    const campoValidade = document.getElementById("editValidade").value;
    const dataFormatada = campoValidade === "" ? null : campoValidade;

    // MONTAGEM DO OBJETO IGUAL AO ProdutoUpdateDTO (Incluindo 'limit')
    const payload = {
        preco: parseFloat(precoLimpo),
        quantidade: parseInt(document.getElementById("editQtd").value),
        limit: parseInt(document.getElementById("editLimit").value), // ENVIA O NOVO CAMPO
        validade: dataFormatada
    };

    console.log("JSON enviado para atualização:", JSON.stringify(payload));

    try {
        const response = await fetch(`http://localhost:8080/api/produtos/${id}`, {
            method: 'PUT',
            headers: { 
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(payload)
        });

        if (response.ok) {
            alert("Produto atualizado com sucesso!");
            fecharModalEdicao();
            carregarEstoque();
        } else {
            const erroTexto = await response.text();
            console.error("Erro do Servidor:", erroTexto);
            alert("Erro ao salvar: " + erroTexto);
        }
    } catch (error) {
        console.error("Erro na requisição:", error);
        alert("Erro de conexão com o servidor.");
    }
}

// 4. INICIALIZAÇÃO
document.addEventListener('DOMContentLoaded', () => {
    carregarEstoque();
    const form = document.getElementById("formEdicao");
    if (form) form.addEventListener("submit", salvarEdicao);
});
// =================================================================================
// 5. SISTEMA DE BUSCA (FILTRO NA TABELA)
// =================================================================================
document.getElementById('inputBusca').addEventListener('keyup', function() {
    const termo = this.value.toLowerCase(); // O que foi digitado (em minúsculo)
    const linhas = document.querySelectorAll('#corpoTabela tr'); // Pega todas as linhas

    linhas.forEach(linha => {
        // Pega o texto de toda a linha (Nome, Categoria, Fornecedor)
        const textoLinha = linha.innerText.toLowerCase();
        
        // Se o texto da linha contiver o termo digitado, mostra. Senão, esconde.
        if (textoLinha.includes(termo)) {
            linha.style.display = '';
        } else {
            linha.style.display = 'none';
        }
    });
});
