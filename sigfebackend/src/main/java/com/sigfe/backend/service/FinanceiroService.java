package com.sigfe.backend.service;

import com.sigfe.backend.exception.BusinessException;
import com.sigfe.backend.model.Compra;
import com.sigfe.backend.model.MovimentacaoFinanceira;
import com.sigfe.backend.model.Venda;
import com.sigfe.backend.model.enums.OrigemMovimentacao;
import com.sigfe.backend.model.enums.StatusVenda;
import com.sigfe.backend.model.enums.TipoMovimentacao;
import com.sigfe.backend.repository.MovimentacaoFinanceiraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FinanceiroService {

    private final MovimentacaoFinanceiraRepository repository;

    public FinanceiroService(MovimentacaoFinanceiraRepository repository) {
        this.repository = repository;
    }

    // 🔹 ENTRADA DE DINHEIRO (Venda paga)
    @Transactional
    public void registrarVenda(Venda venda) {

        if (venda.getStatus() != StatusVenda.PAGA) {
            throw new BusinessException("Venda ainda não está paga");
        }

        MovimentacaoFinanceira movimentacao = new MovimentacaoFinanceira(
                venda.getValorTotal(),
                TipoMovimentacao.ENTRADA,
                OrigemMovimentacao.VENDA
        );

        repository.save(movimentacao);
    }

    // 🔹 SAÍDA DE DINHEIRO (Compra paga)
    @Transactional
    public void registrarCompra(Compra compra) {

        if (!compra.estaPaga()) {
            throw new BusinessException("Compra ainda não está paga");
        }

        MovimentacaoFinanceira movimentacao = new MovimentacaoFinanceira(
                compra.getValorTotal(),
                TipoMovimentacao.SAIDA,
                OrigemMovimentacao.COMPRA
        );

        repository.save(movimentacao);
    }

    // 🔹 SALDO ATUAL
    public BigDecimal calcularSaldo() {

        List<MovimentacaoFinanceira> movimentacoes = repository.findAll();

        return movimentacoes.stream()
                .map(m ->
                        m.getTipo() == TipoMovimentacao.ENTRADA
                                ? m.getValor()
                                : m.getValor().negate()
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
