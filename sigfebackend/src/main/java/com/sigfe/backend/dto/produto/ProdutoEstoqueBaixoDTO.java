package com.sigfe.backend.dto.produto;

import com.sigfe.backend.model.Produto;

public record ProdutoEstoqueBaixoDTO(
        Long id,
        String nome,
        int quantidade
) {
    public ProdutoEstoqueBaixoDTO(Produto produto) {
        this(
                produto.getId(),
                produto.getNome(),
                produto.getQuantidade()
        );
    }
}
