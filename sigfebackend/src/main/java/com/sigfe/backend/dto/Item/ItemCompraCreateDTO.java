package com.sigfe.backend.dto.Item;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record ItemCompraCreateDTO(
        Long produtoId,
        Integer quantidade,
        BigDecimal precoUnitario
) {}
