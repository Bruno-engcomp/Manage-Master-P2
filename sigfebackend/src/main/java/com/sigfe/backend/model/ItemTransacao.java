package com.sigfe.backend.model;


import java.math.BigDecimal; // Importacao da classe BigDecimal e utilizando objeto BigDecimal ou inves de float ou double para obter precisao nos calculos
import jakarta.persistence.*;

@Entity
@Table (name = "item_transacao")
public class ItemTransacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column (nullable = false)
    String produto;

    @Column (nullable = false)
    int quantidade;

    @Column(nullable = false)
    BigDecimal preco;


    @ManyToOne
    @JoinColumn(name = "transacao_id", nullable = false)
    private Transacao transacao;


    public ItemTransacao()
    {

    }
    public ItemTransacao(String produto, int quantidade, BigDecimal preco)
    {
        this.produto = produto;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    // Metodos get


    public Long getId() {return id;}

    public BigDecimal getPreco() {return preco;}

    public String getProduto() {return produto;}

    public int getQuantidade() {return quantidade;}

    public Transacao getTransacao() {return transacao;}

    public BigDecimal getValorTotal() { // Funcao que retorna o valor total da transcao preco x quantidade
        return preco.multiply(BigDecimal.valueOf(quantidade));
    }

    // Metodos Set



    public void setQuantidade(int quantidade)
    {
        if (quantidade > 0)
            this.quantidade = quantidade;
    }

    public void setPreco(BigDecimal preco)
    {
        if(preco.compareTo(BigDecimal.ZERO) > 0)
            this.preco = preco;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public void setTransacao(Transacao transacao) {this.transacao = transacao;}
}

/*No ecossistema Spring Boot (mais especificamente no uso do Spring Data JPA),
 uma Entidade é uma classe Java que representa uma tabela no seu banco de dados relacional.*/
