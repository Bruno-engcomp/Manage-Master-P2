package com.sigfe.backend.model;


import java.math.BigDecimal; // Importacao da classe BigDecimal e utilizando objeto BigDecimal ou inves de float ou double para obter precisao nos calculos

public class ItemTransacao {
    int id;
    String produto;
    int quantidade;
    BigDecimal preco;
    BigDecimal valorTotal;

    public ItemTransacao(int id, String produto, int quantidade, BigDecimal preco)
    {
        if(id > 0)
            this.id = id;
        this.produto = produto;
        if(quantidade > 0)
            this.quantidade = quantidade;
        if(preco.compareTo(BigDecimal.ZERO) > 0)
            this.preco = preco;
    }

    // Metodos get


    public int getId() {return id;}

    public BigDecimal getPreco() {return preco;}

    public String getProduto() {return produto;}

    public int getQuantidade() {return quantidade;}

    public BigDecimal getValorTotal() { // Funcao que retorna o valor total da transcao preco x quantidade
        valorTotal = preco.multiply(BigDecimal.valueOf(quantidade));
        return valorTotal;
    }

    // Metodos Set


    public void setId(int id) {
        this.id = id;
    }

    public void setQuantidade(int quantidade) {
        if (quantidade > 0)
            this.quantidade = quantidade;
    }

    public void setPreco(BigDecimal preco) {
        if(preco.compareTo(BigDecimal.ZERO) > 0)
            this.preco = preco;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

}
