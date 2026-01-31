document.getElementById('supplierForm').addEventListener('submit', async (event) => {
    event.preventDefault();

    // Capturando apenas os campos que existem na sua Entity Fornecedor.java
    const nome = document.getElementById('nome').value;
    const email = document.getElementById('email').value;
    const telefone = document.getElementById('telefone').value;

    // O objeto enviado NÃO deve conter o ID, pois o Spring gera sozinho
    const supplierData = {
        nome: nome,
        email: email,
        telefone: telefone
    };

    console.log("Enviando para o Spring:", supplierData);

    try {
        const response = await fetch('http://localhost:8080/api/fornecedores', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(supplierData)
        });

        if (response.ok) {
            alert("Fornecedor cadastrado com sucesso!");
            window.location.href = "gerenciamento_de_estoque.html";
        } else {
            const errorData = await response.json();
            alert("Erro no Java: " + (errorData.message || "Verifique os logs do console."));
        }
    } catch (error) {
        console.error("Erro de conexão:", error);
        alert("O servidor Spring Boot não respondeu.");
    }
});

// Mantendo suas máscaras de campo para uma boa UX
const telInput = document.getElementById('telefone');
telInput.addEventListener('input', function(e) {
    let value = e.target.value.replace(/\D/g, "");
    value = value.replace(/^(\d{2})(\d)/, "($1) $2");
    value = value.replace(/(\d)(\d{4})$/, "$1-$2");
    e.target.value = value;
});