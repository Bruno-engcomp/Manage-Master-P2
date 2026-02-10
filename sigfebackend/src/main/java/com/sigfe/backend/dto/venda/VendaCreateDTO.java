package com.sigfe.backend.dto.venda;

import com.sigfe.backend.dto.Item.ItemVendaCreateDTO;
import com.sigfe.backend.model.enums.FormaPagamento;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public record VendaCreateDTO(
        // Removi produtoId, quantidade e precoUnitario do topo
        // porque eles já devem estar dentro da lista de 'itens'

        @NotNull(message = "A forma de pagamento é obrigatória")
        FormaPagamento formaPagamento,

        @NotNull(message = "O fornecedor é obrigatório")
        Long fornecedorId,

        @NotBlank(message = "O número do documento é obrigatório")
        String numeroDocumento,


        @NotEmpty(message = "A venda deve ter pelo menos um item")
        @Valid
        List<ItemVendaCreateDTO> itens
) {
}