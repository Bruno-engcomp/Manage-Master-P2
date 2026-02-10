package com.sigfe.backend.controller;

import com.sigfe.backend.dto.venda.VendaCreateDTO;
import com.sigfe.backend.dto.venda.VendaResponseDTO;
import com.sigfe.backend.model.Venda;
import com.sigfe.backend.service.VendaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendas")
@CrossOrigin(origins = "*")
public class VendaController {

    private final VendaService vendaService;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @PostMapping
    public ResponseEntity<VendaResponseDTO> criar(
            @RequestBody @Valid VendaCreateDTO dto) {

        Venda venda = vendaService.salvar(dto);
        return ResponseEntity.ok(new VendaResponseDTO(venda));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendaResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(
                new VendaResponseDTO(vendaService.buscarPorId(id))
        );
    }
}

/*
O Controller é responsável por:

Receber requisicoes HTTP (GET, POST, DELETE, etc.)

Chamar o Service

Retornar respostas HTTP (JSON + status)
* */

