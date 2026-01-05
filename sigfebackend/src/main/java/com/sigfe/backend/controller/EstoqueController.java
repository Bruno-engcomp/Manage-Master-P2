package com.sigfe.backend.controller;

import com.sigfe.backend.dto.estoque.MovimentacaoEstoqueDTO;
import com.sigfe.backend.service.EstoqueService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/estoque")
public class EstoqueController {

    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    // 🔹 ENTRADA DE ESTOQUE
    @PostMapping("/entrada")
    public ResponseEntity<String> entradaEstoque(
            @RequestBody @Valid MovimentacaoEstoqueDTO dto) {

        estoqueService.darEntrada(dto);
        return ResponseEntity.ok("Entrada de estoque realizada com sucesso");
    }

    // 🔹 SAÍDA DE ESTOQUE
    @PostMapping("/saida")
    public ResponseEntity<String> saidaEstoque(
            @RequestBody @Valid MovimentacaoEstoqueDTO dto) {

        estoqueService.darSaida(dto);
        return ResponseEntity.ok("Saída de estoque realizada com sucesso");
    }
}
