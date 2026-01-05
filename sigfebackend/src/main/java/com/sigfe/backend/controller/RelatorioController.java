package com.sigfe.backend.controller;

import com.sigfe.backend.dto.produto.ProdutoEstoqueBaixoDTO;
import com.sigfe.backend.service.RelatorioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    // 🔹 Saldo financeiro
    @GetMapping("/financeiro/saldo")
    public ResponseEntity<BigDecimal> saldoFinanceiro() {
        return ResponseEntity.ok(relatorioService.obterSaldoAtual());
    }

    // 🔹 Estoque baixo
    @GetMapping("/estoque/baixo")
    public ResponseEntity<List<ProdutoEstoqueBaixoDTO>> estoqueBaixo() {
        return ResponseEntity.ok(relatorioService.produtosComEstoqueBaixo());
    }

    // 🔹 Total vendido
    @GetMapping("/vendas/total")
    public ResponseEntity<BigDecimal> totalVendas() {
        return ResponseEntity.ok(relatorioService.totalVendido());
    }
}
