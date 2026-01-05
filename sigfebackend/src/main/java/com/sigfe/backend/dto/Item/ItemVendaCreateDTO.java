package com.sigfe.backend.dto.Item;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ItemVendaCreateDTO(

        @NotNull
        Long produtoId,

        @NotNull
        @Min(1)
        Integer quantidade,

        @NotNull
        BigDecimal preco
) {
}
