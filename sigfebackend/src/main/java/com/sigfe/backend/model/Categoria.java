package com.sigfe.backend.model;


import jakarta.persistence.*;


@Entity
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    public Categoria (Long id, String nome)
    {

        this.id = id;
        this.nome = nome;
    }
    // Metodos set

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {this.nome = nome;}

    // Metodos get

    public Long getId() {return id;}

    public String getNome() {return nome;}
}
