package com.sigfe.backend.model;


import java.math.BigDecimal; // Importacao da classe BigDecimal e utilizando objeto BigDecimal ou inves de float ou double para obter precisao nos calculos
import jakarta.persistence.*;

@Entity
@Table(name = "item_transacao")
public class ItemTransacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @ManyToOne
    @JoinColumn(name = "transacao_id", nullable = false)
    private Transacao transacao;

    //  Construtor padrão
    public ItemTransacao() {}

    //  Construtor opcional
    public ItemTransacao(Produto produto, Integer quantidade, BigDecimal preco) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    // Getters
    public Long getId() { return id; }
    public Produto getProduto() { return produto; }
    public Integer getQuantidade() { return quantidade; }
    public BigDecimal getPreco() { return preco; }
    public Transacao getTransacao() { return transacao; }

    public BigDecimal getValorTotal() {
        return preco.multiply(BigDecimal.valueOf(quantidade));
    }

    // Setters
    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public void setTransacao(Transacao transacao) {
        this.transacao = transacao;
    }
}

/*No ecossistema Spring Boot (mais especificamente no uso do Spring Data JPA),
 uma Entidade é uma classe Java que representa uma tabela no seu banco de dados relacional.*/
