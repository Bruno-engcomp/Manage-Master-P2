package com.sigfe.backend.dto.venda;

import com.sigfe.backend.model.Venda;
import com.sigfe.backend.model.enums.FormaPagamento;

import java.time.LocalDateTime;

public record VendaResponseDTO(

        Long id,
        FormaPagamento formaPagamento,
        String numeroDocumento,
        String status,
        LocalDateTime data
) {
    public VendaResponseDTO(Venda venda) {
        this(
                venda.getId(),
                venda.getFormaPagamento(),
                venda.getNumeroDocumento(),
                venda.getStatus().name(),
                venda.getDataTransacao().atStartOfDay()
        );
    }
}
