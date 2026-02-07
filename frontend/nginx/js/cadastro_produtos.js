document.addEventListener("DOMContentLoaded", async () => {
    // Carrega as listas nos selects ao abrir a página
    await carregarOpcoes("http://localhost:8080/api/categorias", "categoria");
    await carregarOpcoes("http://localhost:8080/api/fornecedores", "fornecedor");
});

async function carregarOpcoes(url, elementId) {
    const select = document.getElementById(elementId);
    try {
        const response = await fetch(url);
        const dados = await response.json();

        select.innerHTML = `<option value="" disabled selected>Selecione...</option>`;
        dados.forEach(item => {
            const option = document.createElement("option");
            option.value = item.id; 
            option.textContent = item.nome; 
            select.appendChild(option);
        });
    } catch (error) {
        console.error(`Erro ao carregar ${elementId}:`, error);
    }
}

document.getElementById("productForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    // Captura os valores dos campos
    const nome = document.getElementById("nome").value;
    const precoRaw = document.getElementById("preco").value; // Ex: "1.250,50"
    const qtd = document.getElementById("qtdInput").value;
    const limit = document.getElementById("limitInput").value; // NOVO: Captura o limite
    const validade = document.getElementById("validadeInput").value;
    const categoriaId = document.getElementById("categoria").value;
    const fornecedorId = document.getElementById("fornecedor").value;

    // Limpa o preço para formato numérico (Java BigDecimal)
    const precoLimpo = parseFloat(precoRaw.replace(/\./g, '').replace(',', '.'));

    // Monta o objeto exatamente igual ao seu ProdutoCreateDTO.java
    const produtoData = {
        nome: nome,
        marca: "Genérico", 
        preco: precoLimpo,
        quantidade: parseInt(qtd),
        limit: parseInt(limit), // NOVO: Enviando para o backend
        validade: validade,
        categoriaId: parseInt(categoriaId),
        fornecedorId: parseInt(fornecedorId)
    };

    console.log("Enviando Produto:", produtoData);

    try {
        const response = await fetch("http://localhost:8080/api/produtos", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(produtoData)
        });

        if (response.ok) {
            alert("Produto cadastrado com sucesso!");
            window.location.href = "gerenciamento_de_estoque.html";
        } else {
            const erroTexto = await response.text();
            console.error("Erro detalhado:", erroTexto);
            alert("Erro do Servidor: " + erroTexto);
        }
    } catch (error) {
        console.error("Erro na requisição:", error);
        alert("Erro de conexão com o servidor.");
    }
});