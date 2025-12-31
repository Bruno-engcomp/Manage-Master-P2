package com.sigfe.backend.model;


import java.math.BigDecimal;
import java.util.List;

public class Venda extends Transacao {
    public Venda(Long id, BigDecimal valorTotal, List<ItemTransacao> itens, String usuario)
    {
        super(id, valorTotal, itens, usuario);
    }
}
