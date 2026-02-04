package com.sigfe.backend.dto.produto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProdutoResponseDTO(
        Long id,
        String nome,
        String marca,
        BigDecimal preco,
        int quantidade,
        LocalDate validade,
        String nomeCategoria,
        String nomeFornecedor, // Este campo DEVE estar aqui no cabeçalho
        boolean vencido
) {
    // Construtor auxiliar: Transforma a Entidade do banco no Pacote (DTO) de saída
    public ProdutoResponseDTO(com.sigfe.backend.model.Produto produto) {
        // A regra do Record: Chamar o construtor principal (canônico) com 'this'
        this(
                produto.getId(),
                produto.getNome(),
                produto.getMarca(),
                produto.getPreco(),
                produto.getQuantidade(),
                produto.getValidade(),
                produto.getCategoria() != null ? produto.getCategoria().getNome() : "Sem Categoria",
                produto.getFornecedor() != null ? produto.getFornecedor().getNome() : "Sem Fornecedor",
                produto.estaVencido()
        );
    }
}