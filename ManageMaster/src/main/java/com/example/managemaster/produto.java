package com.example.managemaster;

public class produto {
    private String nome;
    private String marca;
    private double precoCompra;
    private double preco;
    private int validadedd;
    private int validademm;
    private int validadeyy;

    public produto (String nome, String marca, double preco, int validadedd, int validademm, int validadeyy)
    {
        this.nome = nome;
        this.marca = marca;
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

    private String getNome()
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
}
