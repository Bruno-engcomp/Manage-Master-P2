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


    private Long id; // Long ao inves de int, boa pratica em JPA (Java Persistence) API

    @Column(nullable = false) // impede que o nome seja nulo no banco


    private String nome;
    private String marca;

    @Column(nullable = false, precision = 10, scale = 2)
    // define a precisao correta para os valores monetarios do banco
    private BigDecimal preco; // Uso de BigDecimal, pois ponto flutuante nao e 100% preciso

    @Column(nullable = false) // impede que o quantidade nao seja nulo no banco
    private int quantidade;

    private LocalDate validade;

    @ManyToOne // Muito produtos podem pertencer a uma categoria
    @JoinColumn (name = "categoria_id") // cria a coluna categoria_id como chave estrangeira
    private Categoria categoria;

    protected Produto () {}

    public Produto (String nome, String marca, BigDecimal preco,
                    int quantidade, LocalDate validade, Categoria categoria)
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

    // Metodos set para fazer a alterção dos atributos
    public void setNome(String nome) {this.nome = nome;}

    public void setMarca(String marca) {this.marca = marca;}

    public void setPreco(BigDecimal preco) {
        // Preco.compareTo(BigDecimal.ZERO) e o metodo usado para comparar o valor com zero
            this.preco = preco;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }


    public void setValidade(LocalDate validade) {
        this.validade = validade;
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

    public int getQuantidade() {return quantidade;}

    public LocalDate getValidade() {
        return validade;
    }

    public Categoria getCategoria() {
        return categoria;
    }
}
