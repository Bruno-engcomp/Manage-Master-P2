package com.sigfe.backend.controller;

import com.sigfe.backend.dto.produto.ProdutoCreateDTO;
import com.sigfe.backend.dto.produto.ProdutoResponseDTO;
import com.sigfe.backend.model.Produto;
import com.sigfe.backend.service.ProdutoService;
import jakarta.validation.Valid; // Importado aqui
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criarProduto(@RequestBody @Valid ProdutoCreateDTO dto) {
        // Agora o símbolo 'dto' está claramente definido após a anotação @Valid
        Produto produto = produtoService.salvar(dto);
        return ResponseEntity.ok(new ProdutoResponseDTO(produto));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listarProdutos() {
        List<ProdutoResponseDTO> lista = produtoService.listarTodos()
                .stream()
                .map(ProdutoResponseDTO::new)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Long id) {
        Produto produto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(new ProdutoResponseDTO(produto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerProduto(@PathVariable Long id) {
        produtoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}

/*
O Controller é responsável por:

Receber requisicoes HTTP (GET, POST, DELETE, etc.)

Chamar o Service

Retornar respostas HTTP (JSON + status)
* */
