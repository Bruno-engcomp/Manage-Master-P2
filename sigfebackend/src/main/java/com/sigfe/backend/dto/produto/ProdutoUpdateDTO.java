package com.sigfe.backend.dto.produto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProdutoUpdateDTO (
        BigDecimal preco,
        Integer quantidade,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate validade
) {
}
