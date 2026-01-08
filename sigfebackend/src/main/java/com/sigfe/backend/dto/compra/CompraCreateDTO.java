package com.sigfe.backend.dto.compra;

import com.sigfe.backend.dto.Item.ItemCompraDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CompraCreateDTO(

        @NotBlank
        String usuario,

        @NotBlank
        String numeroDocumento,

        @NotBlank
        String formaPagamento,

        @NotNull
        Long fornecedorId,

        @NotNull
        List<ItemCompraDTO> itens

) {}
