package com.sigfe.backend.controller;

import com.sigfe.backend.dto.categoria.CategoriaCreateDTO;
import com.sigfe.backend.dto.categoria.CategoriaResponseDTO;
import com.sigfe.backend.service.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin (origins = "*")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    //  CREATE
    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> criar(
            @RequestBody CategoriaCreateDTO dto) {

        return ResponseEntity.ok(service.salvar(dto));
    }

    // READ - LISTAR
    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // READ - BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody CategoriaCreateDTO dto) {

        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
