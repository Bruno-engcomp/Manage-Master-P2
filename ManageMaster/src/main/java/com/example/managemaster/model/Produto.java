package com.example.managemaster.model;

public class produto {
    private int id;
    private String nome;
    private String marca;
    private double precoCompra;
    private double preco;
    private int validadedd;
    private int validademm;
    private int validadeyy;
    private int quantidade;

    public produto (String nome ,int id, String marca, double preco,int quantidade,int validadedd, int validademm, int validadeyy)
    {
        // Validação dos atributos
        this.nome = nome;
        this.marca = marca;
        if(id > 0)
            this.id = id;
        if(quantidade > 0)
            this.quantidade = quantidade;
        if(preco > 0.0)
        {
            this.preco = preco;
        }
        if(validadedd > 0 && validadedd < 32)
            this.validadedd = validadedd;
        if(validademm > 0 && validademm < 13)
            this.validademm = validademm;
        if(validadeyy > 2024)
            this.validadeyy = validadeyy;
    }

    // Metodos set para fazer a alterção dos atributos
    public void setNome(String nome) {this.nome = nome;}
    public void setMarca(String marca) {this.marca = marca;}
    public void setPreco(double preco) {
        if(preco > 0)
            this.preco = preco;
    }
    public void setValidadedd(int validadedd)
    {
        if(validadedd > 0 && validadedd < 32)
            this.validadedd = validadedd;
    }

    public void setValidademm(int validademm) {
        if(validademm > 0 && validademm < 13)
            this.validademm = validademm;
    }

    public void setValidadeyy(int validadeyy) {
        if(validadeyy > 2024)
            this.validadeyy = validadeyy;
    }

    public void setQuantidade(int quantidade) {
        if(quantidade > 0)
            this.quantidade = quantidade;
    }

    public void setId(int id) {
        if (id > 0)
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

    public double getPreco()
    {
        return preco;
    }

    public int getValidadedd()
    {
        return validadedd;
    }

    public int getValidademm()
    {
        return validademm;
    }

    public int getValidadeyy()
    {
        return validadeyy;
    }

    public int getId() {return id;}

    public int getQuantidade() {return quantidade;}
}
