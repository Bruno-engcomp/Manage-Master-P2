document.addEventListener("DOMContentLoaded", async () => {
    // Carrega as categorias existentes para popular o datalist (ajuda o usuário a não duplicar)
    await carregarSugestoesCategorias();
});

// Função para buscar categorias e colocar no datalist
async function carregarSugestoesCategorias() {
    const datalist = document.getElementById("listaCategoriasDB");
    try {
        const response = await fetch("http://localhost:8080/api/categorias");
        const categorias = await response.json();

        datalist.innerHTML = ""; // Limpa a lista
        categorias.forEach(cat => {
            const option = document.createElement("option");
            option.value = cat.nome; // O datalist usa o valor para sugestão
            datalist.appendChild(option);
        });
    } catch (error) {
        console.error("Erro ao carregar sugestões de categorias:", error);
    }
}

// Evento de envio do formulário
document.getElementById("categoryForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    // Monta o objeto seguindo o CategoriaCreateDTO
    const categoriaData = {
        nome: document.getElementById("inputCategoria").value
    };

    try {
        const response = await fetch("http://localhost:8080/api/categorias", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(categoriaData)
        });

        if (!response.ok) {
            const erroTexto = await response.text();
            throw new Error(erroTexto || "Erro ao salvar categoria");
        }

        alert("Categoria salva com sucesso!");
        // Redireciona de volta para o estoque
        window.location.href = "gerenciamento_de_estoque.html";

    } catch (error) {
        alert("Erro ao cadastrar: " + error.message);
        console.error("Erro completo:", error);
    }
});