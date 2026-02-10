package com.sigfe.backend.model;

import com.sigfe.backend.model.enums.FormaPagamento;
import com.sigfe.backend.model.enums.StatusVenda;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "venda")
@DiscriminatorValue("VENDA")
public class Venda extends Transacao {

    @ManyToOne
    @JoinColumn(name = "fornecedor_id", nullable = true)
    private Fornecedor fornecedor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FormaPagamento formaPagamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusVenda status;

    @Column(nullable = false)
    private String numeroDocumento;

    @Column(name = "valor_total", precision = 10, scale = 2)
    private BigDecimal valorTotal;

    // Construtor obrigatório para o JPA
    public Venda() {
        this.status = StatusVenda.ABERTA;
    }

    // Construtor de domínio
    public Venda(Long id,
                 List<ItemTransacao> itens,
                 String usuario,
                 Fornecedor fornecedor,
                 FormaPagamento formaPagamento,
                 String numeroDocumento) {

        super(itens, usuario); // ✅ agora existe

        this.fornecedor = fornecedor;
        this.formaPagamento = formaPagamento;
        this.numeroDocumento = numeroDocumento;
        this.status = StatusVenda.ABERTA;
    }

    // GETTERS E SETTERS

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public StatusVenda getStatus() {
        return status;
    }

    public void setStatus(StatusVenda status) {
        this.status = status;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }



    @PrePersist
    @PreUpdate
    public void calcularTotalAutomatico() {
        this.valorTotal = (itens == null) ? BigDecimal.ZERO :
                itens.stream()
                        .map(item -> item.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
