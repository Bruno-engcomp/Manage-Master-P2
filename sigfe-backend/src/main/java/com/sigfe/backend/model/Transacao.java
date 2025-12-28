package com.sigfe.backend.model;


import java.time.LocalDate; // Biblioteca LocalDate para data atual de forma segura
import java.math.BigDecimal; // Importacao da classe BigDecimal e utilizando objeto BigDecimal ou inves de float ou double para obter precisao nos calculos
import java.util.List; // Para fazer listas

public class Transacao {
    private int id; // Id da transacao
    private LocalDate dataTransacao; // Data da transacao
    private BigDecimal valorTotal; // Valor total da transacao
    private List<ItemTransacao> itens; // Os itens que estavam incluidos na transacao
    private String usuario; // Usuario que realizou a transacao

    public Transacao(int id, BigDecimal valorTotal, List<ItemTransacao> itens, String usuario)
    {
        if (id > 0)
            this.id = id;
        if (valorTotal.compareTo(BigDecimal.ZERO) > 0)
            this.valorTotal = valorTotal;
        this.dataTransacao = dataTransacao;
        this.itens = itens;
        this.usuario = usuario;
    }

    // Getters para poder usar os valores em outros lugares

    public int getId() {return id;}

    public LocalDate getDataTransacao() {return dataTransacao;}

    public BigDecimal getValorTotal() {return valorTotal;}

    public List<ItemTransacao> getItens() {return itens;}

    public String getUsuario() {return usuario;}

    // Setters para poder alteras os valores dos atributos

    public void setId(int id) {
        if (id>0)
            this.id = id;
    }

    public void setDataTransacao(LocalDate dataTransacao) {this.dataTransacao = dataTransacao;}

    public void setItens(List<ItemTransacao> itens) {this.itens = itens;}

    public void setUsuario(String usuario) {this.usuario = usuario;}

    public void setValorTotal(BigDecimal valorTotal) {
        if(valorTotal.compareTo(BigDecimal.ZERO) > 0)
            this.valorTotal = valorTotal;
    }
}
