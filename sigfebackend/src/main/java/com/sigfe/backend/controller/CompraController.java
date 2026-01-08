package com.sigfe.backend.controller;

import com.sigfe.backend.dto.compra.CompraCreateDTO;
import com.sigfe.backend.dto.compra.CompraResponseDTO;
import com.sigfe.backend.model.Compra;
import com.sigfe.backend.service.CompraService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compras")
public class CompraController {

    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @PostMapping
    public ResponseEntity<CompraResponseDTO> criar(
            @RequestBody @Valid CompraCreateDTO dto
    ) {
        Compra salva = compraService.salvar(dto);
        return ResponseEntity.status(201).body(new CompraResponseDTO(salva));
    }


    @GetMapping
    public ResponseEntity<List<CompraResponseDTO>> listar() {
        List<CompraResponseDTO> lista = compraService.listarTodas()
                .stream()
                .map(CompraResponseDTO::new)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraResponseDTO> buscarPorId(@PathVariable Long id) {
        Compra compra = compraService.buscarPorId(id);
        return ResponseEntity.ok(new CompraResponseDTO(compra));
    }
}
/*
O Controller é responsável por:

Receber requisicoes HTTP (GET, POST, DELETE, etc.)

Chamar o Service

Retornar respostas HTTP (JSON + status)
* */

