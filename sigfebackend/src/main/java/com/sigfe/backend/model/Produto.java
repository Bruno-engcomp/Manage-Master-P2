package com.sigfe.backend.model;


import jakarta.persistence.*;
// Importa as anotacoes do JPA usadas para mapear a classe no banco de dados
import java.math.BigDecimal;
// Importacao da classe BigDecimal e utilizando objeto BigDecimal ou inves de float ou double para obter precisao nos calculos
import java.time.LocalDate;

@Entity
// indica que esta classe e uma entidade JPA (vira uma tabela)
@Table(name = "produtos")
// Define o nome da tabela no Banco

public class Produto {

    @Id
    // define a chave primaria da tabela
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    // O banco gera o ID automaticamente    (auto incremento)


    private Long id; // Long ao inves de Integer

    @Column(nullable = false) // impede que o nome seja nulo no banco


    private String nome;
    private String marca;

    @Column(nullable = false, precision = 10, scale = 2)
    // define a precisao correta para os valores monetarios do banco
    private BigDecimal preco; // Uso de BigDecimal, pois ponto flutuante nao e 100% preciso

    @Column(nullable = false) // impede que o quantidade nao seja nulo no banco
    private Integer quantidade;

    @Column(name = "estoque_minimo")
    private Integer limit;

    private LocalDate validade;

    @ManyToOne // Muito produtos podem pertencer a uma categoria
    @JoinColumn (name = "categoria_id") // cria a coluna categoria_id como chave estrangeira
    private Categoria categoria;

    @ManyToOne
    @JoinColumn (name = "fornecedor_id")
    private Fornecedor fornecedor;

    public Produto() {}

    public Produto (String nome, String marca, BigDecimal preco,
                    Integer quantidade, LocalDate validade, Categoria categoria)
    {
        // Validação dos atributos
        this.nome = nome;
        this.marca = marca;

        this.validade = validade;
        this.categoria = categoria;
            this.quantidade = quantidade;
         // Preco.compareTo(BigDecimal.ZERO) e o metodo usado para comparar o valor com zero
            this.preco = preco;


    }

    // Aumenta o estoque do produto
    public void adicionarEstoque(Integer quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        this.quantidade += quantidade;
    }

    // Diminui o estoque do produto
    public void removerEstoque(Integer quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        if (this.quantidade < quantidade) {
            throw new IllegalStateException("Estoque insuficiente");
        }
        this.quantidade -= quantidade;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public void alterarPreco(BigDecimal novoPreco) {
        if (novoPreco == null || novoPreco.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Preço inválido");
        }
        this.preco = novoPreco;
    }
    //VERIFICACAO DE VALIDADE
    public boolean estaVencido() {
        return validade != null && validade.isBefore(LocalDate.now());
    }




    // Metodos set para fazer a alterção dos atributos
    public void setNome(String nome) {this.nome = nome;}

    public void setMarca(String marca) {this.marca = marca;}

    public void setPreco(BigDecimal preco) {this.preco = preco;}

    public void setValidade(LocalDate validade) {this.validade = validade;}

    public void setCategoria(Categoria categoria) {this.categoria = categoria;}

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Metodos get que servem para poder usar o valor dentro dos atributos de forma segura
    public String getNome()
    {
        return nome;
    }

    public String getMarca()
    {
        return marca;
    }

    public BigDecimal getPreco() // Preco.compareTo(BigDecimal.ZERO) e o metodo usado para comparar o valor com zero
    {
        return preco;
    }

    public Long getId() {return id;}

    public Integer getQuantidade() {return quantidade;}

    public LocalDate getValidade() {
        return validade;
    }

    public Categoria getCategoria() {
        return categoria;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produto)) return false;
        Produto produto = (Produto) o;
        return id != null && id.equals(produto.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

/*No ecossistema Spring Boot (mais especificamente no uso do Spring Data JPA),
 uma Entidade é uma classe Java que representa uma tabela no seu banco de dados relacional.*/
