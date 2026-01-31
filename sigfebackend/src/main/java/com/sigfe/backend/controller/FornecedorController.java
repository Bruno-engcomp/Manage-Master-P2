package com.sigfe.backend.controller;

import com.sigfe.backend.dto.fornecedor.FornecedorCreateDTO;
import com.sigfe.backend.dto.fornecedor.FornecedorResponseDTO;
import com.sigfe.backend.service.FornecedorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/fornecedores")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class FornecedorController {

    private final FornecedorService fornecedorService;

    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    // CREATE - Agora recebe CreateDTO e retorna ResponseDTO
    @PostMapping
    public ResponseEntity<FornecedorResponseDTO> criarFornecedor(
            @RequestBody @Valid FornecedorCreateDTO dto) {

        // O Service retorna FornecedorResponseDTO, então a variável deve ser do mesmo tipo
        FornecedorResponseDTO fornecedorSalvo = fornecedorService.salvar(dto);
        return ResponseEntity.ok(fornecedorSalvo);
    }

    // READ - Lista todos usando DTO
    @GetMapping
    public ResponseEntity<List<FornecedorResponseDTO>> listarTodos() {
        return ResponseEntity.ok(fornecedorService.listar());
    }

    // READ - Buscar por ID retornando DTO
    @GetMapping("/{id}")
    public ResponseEntity<FornecedorResponseDTO> buscarPorId(
            @PathVariable Long id) {

        FornecedorResponseDTO dto = fornecedorService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    // DELETE - Permanece igual (Void)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerFornecedor(
            @PathVariable Long id) {

        fornecedorService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
/*
O Controller é responsável por:

Receber requisicoes HTTP (GET, POST, DELETE, etc.)

Chamar o Service

Retornar respostas HTTP (JSON + status)
* */
