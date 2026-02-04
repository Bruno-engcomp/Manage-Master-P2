package com.sigfe.backend.dto.produto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProdutoCreateDTO (
        String nome,
        String marca,
        BigDecimal preco,
        Integer quantidade,
        LocalDate validade,
        Long categoriaId,
        Long fornecedorId // ADICIONE ESTA LINHA
) {
}