package com.sigfe.backend.model;


import jakarta.persistence.*;
// Importa as anotacoes do JPA usadas para mapear a classe no banco de dados
import java.time.LocalDate; // Biblioteca LocalDate para data atual de forma segura
import java.math.BigDecimal; // Importacao da classe BigDecimal e utilizando objeto BigDecimal ou inves de float ou double para obter precisao nos calculos
import java.util.List; // Para fazer listas

@Entity
// indica que esta classe e uma entidade JPA (vira uma tabela)
@Table (name = "Transacao")
// Define o nome da tabela no Banco
public class Transacao {

    @Id
    // define a chave primaria da tabela
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    // O banco gera o ID automaticamente    (auto incremento)

    private Long id; // Id da transacao

    private LocalDate dataTransacao; // Data da transacao


    @OneToMany(mappedBy = "transacao", cascade = CascadeType.ALL)
    private List<ItemTransacao> itens; // Os itens que estavam incluidos na transacao

    @Column (nullable = false)
    private String usuario; // Usuario que realizou a transacao


    public Transacao ()
    {
        this.dataTransacao = LocalDate.now();
    }
    public Transacao(Long id, List<ItemTransacao> itens, String usuario)
    {
        this.id = id;
        this.itens = itens;
        this.usuario = usuario;
        this.dataTransacao = LocalDate.now();

    }

    // Getters para poder usar os valores em outros lugares

    public Long getId() {return id;}

    public LocalDate getDataTransacao() {return dataTransacao;}

    public BigDecimal getValorTotal() {
        return itens.stream()
                .map(ItemTransacao::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    public List<ItemTransacao> getItens() {return itens;}

    public String getUsuario() {return usuario;}

    // Setters para poder alteras os valores dos atributos

    public void setDataTransacao(LocalDate dataTransacao) {this.dataTransacao = dataTransacao;}

    public void setItens(List<ItemTransacao> itens) {
        this.itens = itens;
        itens.forEach(item -> item.setTransacao(this));
    }

    public void setUsuario(String usuario) {this.usuario = usuario;}


}
