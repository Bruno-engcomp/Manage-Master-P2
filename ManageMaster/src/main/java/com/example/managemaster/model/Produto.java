package com.example.managemaster.model;

import java.time.LocalDate;

public class Produto {
    private int id;
    private String nome;
    private String marca;
    private double preco;
    private int quantidade;
    private LocalDate validade;

    public Produto (String nome ,int id, String marca, double preco,int quantidade, LocalDate validade)
    {
        // Validação dos atributos
        this.nome = nome;
        this.marca = marca;
        this.validade = validade;
        if(id > 0)
            this.id = id;
        if(quantidade > 0)
            this.quantidade = quantidade;
        if(preco > 0.0)
        {
            this.preco = preco;
        }

    }

    // Metodos set para fazer a alterção dos atributos
    public void setNome(String nome) {this.nome = nome;}
    public void setMarca(String marca) {this.marca = marca;}
    public void setPreco(double preco) {
        if(preco > 0)
            this.preco = preco;
    }

    public void setQuantidade(int quantidade) {
        if(quantidade > 0)
            this.quantidade = quantidade;
    }

    public void setId(int id) {
        if (id > 0)
            this.id = id;
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

    public double getPreco()
    {
        return preco;
    }

    public int getId() {return id;}

    public int getQuantidade() {return quantidade;}

    public LocalDate getValidade() {
        return validade;
    }
}
