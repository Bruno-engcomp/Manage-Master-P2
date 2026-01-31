document.addEventListener("DOMContentLoaded", async () => {
    // Carrega as listas assim que a página abre
    await carregarOpcoes("http://localhost:8080/api/categorias", "categoria");
    await carregarOpcoes("http://localhost:8080/api/fornecedores", "fornecedor");
});

// Função genérica para preencher qualquer Select
async function carregarOpcoes(url, elementId) {
    const select = document.getElementById(elementId);
    try {
        const response = await fetch(url);
        const dados = await response.json();

        select.innerHTML = `<option value="" disabled selected>Selecione...</option>`;
        dados.forEach(item => {
            const option = document.createElement("option");
            option.value = item.id; // Envia o ID para o Java
            option.textContent = item.nome; // Exibe o Nome para o usuário
            select.appendChild(option);
        });
    } catch (error) {
        console.error(`Erro ao carregar ${elementId}:`, error);
    }
}

// Evento de envio do formulário
document.getElementById("productForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const produtoData = {
        nome: document.getElementById("nome").value,
        preco: parseFloat(document.getElementById("preco").value),
        quantidade: parseInt(document.getElementById("qtdInput").value),
        validade: document.getElementById("validadeInput").value,
        categoriaId: parseInt(document.getElementById("categoria").value),
        fornecedorId: parseInt(document.getElementById("fornecedor").value) 
    };

    try {
        const response = await fetch("http://localhost:8080/api/produtos", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(produtoData)
        });

        if (!response.ok) throw new Error(await response.text());

        alert("Produto cadastrado com sucesso!");
        window.location.href = "gerenciamento_de_estoque.html";
    } catch (error) {
        alert("Erro ao cadastrar: " + error.message);
    }
});