package com.sigfe.backend.controller;

import com.sigfe.backend.model.Venda;
import com.sigfe.backend.service.VendaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendas")
public class VendaController {

    private final VendaService vendaService;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    // 🔹 Criar venda
    @PostMapping
    public ResponseEntity<Venda> criar(@RequestBody Venda venda) {
        Venda novaVenda = vendaService.salvar(venda);
        return ResponseEntity.ok(novaVenda);
    }

    // 🔹 Listar todas
    @GetMapping
    public ResponseEntity<List<Venda>> listar() {
        return ResponseEntity.ok(vendaService.listarTodas());
    }

    // 🔹 Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Venda> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vendaService.buscarPorId(id));
    }

    // 🔹 Atualizar venda
    @PutMapping("/{id}")
    public ResponseEntity<Venda> atualizar(
            @PathVariable Long id,
            @RequestBody Venda venda) {

        return ResponseEntity.ok(vendaService.atualizar(id, venda));
    }

    // 🔹 Deletar venda
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        vendaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

/*
O Controller é responsável por:

Receber requisicoes HTTP (GET, POST, DELETE, etc.)

Chamar o Service

Retornar respostas HTTP (JSON + status)
* */

