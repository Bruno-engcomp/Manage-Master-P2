package com.sigfe.backend.controller;

import com.sigfe.backend.dto.produto.ProdutoCreateDTO;
import com.sigfe.backend.dto.produto.ProdutoResponseDTO;
import com.sigfe.backend.dto.produto.ProdutoUpdateDTO;
import com.sigfe.backend.model.Produto;
import com.sigfe.backend.repository.ProdutoRepository;
import com.sigfe.backend.service.ProdutoService;
import jakarta.validation.Valid; // Importado aqui
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criarProduto(
            @RequestBody @Valid ProdutoCreateDTO dto) {

        Produto produto = produtoService.salvar(dto);
        return ResponseEntity.status(201)
                .body(new ProdutoResponseDTO(produto));
    }

    @Autowired
    private ProdutoRepository repository;

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody ProdutoUpdateDTO dto) {
        try {

            Produto produtoExistente = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            produtoExistente.setPreco(dto.preco());
            produtoExistente.setQuantidade(dto.quantidade());
            produtoExistente.setValidade(dto.validade());
            produtoExistente.setLimit(dto.limit());

            repository.save(produtoExistente);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // Isso vai ajudar a ver erros futuros no console do navegador
            return ResponseEntity.badRequest().body("Erro no Java: " + e.getMessage());
        }
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
