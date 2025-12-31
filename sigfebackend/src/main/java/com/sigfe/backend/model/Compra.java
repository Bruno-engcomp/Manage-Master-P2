package com.sigfe.backend.model;



import main.java.com.sigfe.backend.model.enums.FormaPagamento;
import main.java.com.sigfe.backend.model.enums.StatusCompra;

import java.math.BigDecimal;
import java.util.List;

public class Compra extends Transacao {

    private Fornecedor fornecedor;
    private FormaPagamento formaPagamento;
    private StatusCompra status;
    private String numeroDocumento;
    public Compra(Long id, BigDecimal valorTotal, List<ItemTransacao> itens, String usuario,
                  Fornecedor fornecedor, FormaPagamento formaPagamento, String numeroDocumento) {
        super(id, valorTotal, itens, usuario);
        this.fornecedor = fornecedor;
        this.formaPagamento = formaPagamento;
        this.numeroDocumento = numeroDocumento;
    }

    public void marcarComoPaga()
    {
        if(this.status == StatusCompra.PENDENTE)
            this.status = StatusCompra.PAGA;
    }
    public void cancelar()
    {
        if(this.status != StatusCompra.PAGA)
            this.status = StatusCompra.CANCELADA;
    }
    public boolean estaPaga()
    {
        return this.status == StatusCompra.PAGA;
    }

    //GETTERS


    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public StatusCompra getStatus() {
        return status;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }
}
