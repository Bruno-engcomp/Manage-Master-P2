package com.sigfe.backend.model;

public class Fornecedor {
    private Long id;
    private String nome;
    private String email;
    private int telefone;

    public Fornecedor (Long id, String nome, String email, int telefone)
    {
        if(id <= 0)
            throw new IllegalArgumentException("ID deve ser maior que zero");
        if(telefone <= 0)
            throw new IllegalArgumentException("Telefone deve ser maior que zero");

        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public Long getId() {
        return id;
    }

    public int getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public void setId(Long id) {
        if (id>0)
            this.id = id;
    }

    public void setTelefone(int telefone) {
        if(telefone > 0)
            this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
