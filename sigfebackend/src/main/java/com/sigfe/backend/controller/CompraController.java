package com.sigfe.backend.controller;

import com.sigfe.backend.model.Compra;
import com.sigfe.backend.service.CompraService;
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

    // 🔹 Criar compra
    @PostMapping
    public ResponseEntity<Compra> criar(@RequestBody Compra compra) {
        return ResponseEntity.ok(compraService.salvar(compra));
    }

    // 🔹 Listar todas
    @GetMapping
    public ResponseEntity<List<Compra>> listar() {
        return ResponseEntity.ok(compraService.listarTodas());
    }

    // 🔹 Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Compra> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(compraService.buscarPorId(id));
    }

    // 🔹 Atualizar compra
    @PutMapping("/{id}")
    public ResponseEntity<Compra> atualizar(
            @PathVariable Long id,
            @RequestBody Compra compra) {

        return ResponseEntity.ok(compraService.atualizar(id, compra));
    }

    // 🔹 Marcar como paga
    @PutMapping("/{id}/pagar")
    public ResponseEntity<Void> pagar(@PathVariable Long id) {
        compraService.marcarComoPaga(id);
        return ResponseEntity.noContent().build();
    }

    // 🔹 Cancelar compra
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        compraService.cancelar(id);
        return ResponseEntity.noContent().build();
    }

    // 🔹 Deletar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        compraService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
