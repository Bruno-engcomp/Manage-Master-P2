package com.sigfe.backend.model;

import jakarta.persistence.*;
import com.sigfe.backend.model.enums.FormaPagamento;
import com.sigfe.backend.model.enums.StatusCompra;

import java.util.List;

@Entity
@Table(name = "compra")
@DiscriminatorValue("COMPRA")
public class Compra extends Transacao {

    @ManyToOne
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private Fornecedor fornecedor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FormaPagamento formaPagamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusCompra status;

    @Column(nullable = false)
    private String numeroDocumento;

    // Construtor vazio obrigatório para o JPA
    public Compra() {
        this.status = StatusCompra.PENDENTE;
    }

    public Compra(List<ItemTransacao> itens, String usuario,
                  Fornecedor fornecedor, FormaPagamento formaPagamento,
                  String numeroDocumento) {

        super(itens, usuario);
        this.fornecedor = fornecedor;
        this.formaPagamento = formaPagamento;
        this.numeroDocumento = numeroDocumento;
        this.status = StatusCompra.PENDENTE;
    }

    // Regras de negócio
    public void marcarComoPaga() {
        if (this.status == StatusCompra.PENDENTE) {
            this.status = StatusCompra.PAGA;
        }
    }

    public void cancelar() {
        if (this.status != StatusCompra.PAGA) {
            this.status = StatusCompra.CANCELADA;
        }
    }

    public boolean estaPaga() {
        return this.status == StatusCompra.PAGA;
    }


    // Getters
    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }
    public StatusCompra getStatus() {
        return status;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {this.numeroDocumento = numeroDocumento;}
}
