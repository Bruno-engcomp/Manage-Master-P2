package com.sigfe.backend.service;

import com.sigfe.backend.dto.produto.ProdutoEstoqueBaixoDTO;
import com.sigfe.backend.model.MovimentacaoFinanceira;
import com.sigfe.backend.model.enums.TipoMovimentacao;
import com.sigfe.backend.repository.MovimentacaoFinanceiraRepository;
import com.sigfe.backend.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RelatorioService {

    private final MovimentacaoFinanceiraRepository financeiroRepository;
    private final ProdutoRepository produtoRepository;

    public RelatorioService(MovimentacaoFinanceiraRepository financeiroRepository,
                            ProdutoRepository produtoRepository) {
        this.financeiroRepository = financeiroRepository;
        this.produtoRepository = produtoRepository;
    }

    // 🔹 Saldo atual
    public BigDecimal obterSaldoAtual() {
        return financeiroRepository.findAll()
                .stream()
                .map(m ->
                        m.getTipo() == TipoMovimentacao.ENTRADA
                                ? m.getValor()
                                : m.getValor().negate()
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // 🔹 Produtos com estoque baixo
    public List<ProdutoEstoqueBaixoDTO> produtosComEstoqueBaixo() {
        return produtoRepository.findByQuantidadeLessThanEqual(5)
                .stream()
                .map(ProdutoEstoqueBaixoDTO::new)
                .toList();
    }

    // 🔹 Total vendido
    public BigDecimal totalVendido() {
        return financeiroRepository.findAll()
                .stream()
                .filter(m -> m.getTipo() == TipoMovimentacao.ENTRADA)
                .map(MovimentacaoFinanceira::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
