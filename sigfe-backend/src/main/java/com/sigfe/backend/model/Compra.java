package com.sigfe.backend.model;


import java.math.BigDecimal;
import java.util.List;

public class Compra extends Transacao{
    public Compra(int id, BigDecimal valorTotal, List<ItemTransacao> itens, String usuario) {
        super(id, valorTotal, itens, usuario);
    }
}
