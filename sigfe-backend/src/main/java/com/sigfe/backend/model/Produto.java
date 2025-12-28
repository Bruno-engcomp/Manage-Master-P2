package com.sigfe.backend.model;



import java.time.LocalDate;
import java.math.BigDecimal; // Importacao da classe BigDecimal e utilizando objeto BigDecimal ou inves de float ou double para obter precisao nos calculos

public class Produto {
    private int id;
    private String nome;
    private String marca;
    private BigDecimal preco; // Uso de BigDecimal, pois ponto flutuante nao e 100% preciso
    private int quantidade;
    private LocalDate validade;
    private Categoria categoria;

    public Produto (String nome ,int id, String marca, BigDecimal preco,int quantidade, LocalDate validade, Categoria categoria)
    {
        // Validação dos atributos
        this.nome = nome;
        this.marca = marca;

        this.validade = validade;
        this.categoria = categoria;
        if(id > 0)
            this.id = id;
        if(quantidade > 0)
            this.quantidade = quantidade;
        if(preco.compareTo(BigDecimal.ZERO) > 0) // Preco.compareTo(BigDecimal.ZERO) e o metodo usado para comparar o valor com zero
        {
            this.preco = preco;
        }

    }

    // Metodos set para fazer a alterção dos atributos
    public void setNome(String nome) {this.nome = nome;}
    public void setMarca(String marca) {this.marca = marca;}
    public void setPreco(BigDecimal preco) {
        if(preco.compareTo(BigDecimal.ZERO) > 0) // Preco.compareTo(BigDecimal.ZERO) e o metodo usado para comparar o valor com zero
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

    public BigDecimal getPreco() // Preco.compareTo(BigDecimal.ZERO) e o metodo usado para comparar o valor com zero
    {
        return preco;
    }

    public int getId() {return id;}

    public int getQuantidade() {return quantidade;}

    public LocalDate getValidade() {
        return validade;
    }

    public Categoria getCategoria() {
        return categoria;
    }
}
