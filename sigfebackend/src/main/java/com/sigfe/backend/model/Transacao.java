package com.sigfe.backend.model;



import java.time.LocalDate; // Biblioteca LocalDate para data atual de forma segura
import java.math.BigDecimal; // Importacao da classe BigDecimal e utilizando objeto BigDecimal ou inves de float ou double para obter precisao nos calculos
import java.util.List; // Para fazer listas

public class Transacao {
    private Long id; // Id da transacao
    private LocalDate dataTransacao; // Data da transacao
    private BigDecimal valorTotal; // Valor total da transacao
    private List<ItemTransacao> itens; // Os itens que estavam incluidos na transacao
    private String usuario; // Usuario que realizou a transacao

    public Transacao(Long id, BigDecimal valorTotal, List<ItemTransacao> itens, String usuario)
    {
        if (id <= 0)
            throw new IllegalArgumentException("ID deve ser maior que zero");

        if (valorTotal == null  ||valorTotal.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Valor total inválido");
        this.dataTransacao = dataTransacao;

        this.id = id;
        this.itens = itens;
        this.valorTotal = valorTotal;
        this.usuario = usuario;
        this.dataTransacao = LocalDate.now();

    }

    // Getters para poder usar os valores em outros lugares

    public Long getId() {return id;}

    public LocalDate getDataTransacao() {return dataTransacao;}

    public BigDecimal getValorTotal() {return valorTotal;}

    public List<ItemTransacao> getItens() {return itens;}

    public String getUsuario() {return usuario;}

    // Setters para poder alteras os valores dos atributos

    public void setId(Long id) {
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
