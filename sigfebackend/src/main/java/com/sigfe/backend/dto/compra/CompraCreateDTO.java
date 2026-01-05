package com.sigfe.backend.dto.compra;

import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;

public record CompraCreateDTO(
        @NotBlank String usuario,
        @NotBlank String numeroDocumento,
        Long fornecedorId,
        BigDecimal valorTotal
) {}
