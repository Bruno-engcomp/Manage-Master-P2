package com.sigfe.backend.dto.venda;

import com.sigfe.backend.dto.Item.ItemVendaCreateDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record VendaCreateDTO(

        @NotNull
        Long fornecedorId,

        @NotBlank
        String formaPagamento,

        @NotBlank
        String numeroDocumento,

        @NotNull
        List<ItemVendaCreateDTO> itens
) {
}
