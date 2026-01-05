package com.sigfe.backend.dto.estoque;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record MovimentacaoEstoqueDTO(

        @NotNull(message = "ID do produto é obrigatório")
        Long produtoId,

        @NotNull(message = "Quantidade é obrigatória")
        @Min(value = 1, message = "Quantidade deve ser maior que zero")
        Integer quantidade
) {
}
