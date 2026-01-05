package com.sigfe.backend.dto.compra;

import com.sigfe.backend.model.Compra;
import java.math.BigDecimal;

public record CompraResponseDTO(
        Long id,
        String usuario,
        String numeroDocumento,
        String status,
        BigDecimal valorTotal
) {
    // Construtor compacto para conversão automática
    public CompraResponseDTO(Compra compra) {
        this(
                compra.getId(),
                compra.getUsuario(),
                compra.getNumeroDocumento(),
                compra.getStatus() != null ? compra.getStatus().name() : "PENDENTE",
                compra.getValorTotal()
        );
    }
}