package com.sigfe.backend.model;


public class Categoria {
    private int id;
    private String nome;

    public Categoria (int id, String nome)
    {
        if(id > 0)
           this.id = id;
        this.nome = nome;
    }
    // Metodos set

    public void setId(int id) {
        if (id > 0)
            this.id = id;
    }

    public void setNome(String nome) {this.nome = nome;}

    // Metodos get

    public int getId() {return id;}

    public String getNome() {return nome;}
}
