package com.sigfe.backend.service;

import com.sigfe.backend.dto.estoque.MovimentacaoEstoqueDTO;
import com.sigfe.backend.exception.BusinessException;
import com.sigfe.backend.model.Produto;
import com.sigfe.backend.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class EstoqueService {

    private final ProdutoRepository produtoRepository;

    public EstoqueService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    // Método que o VendaService vai usar diretamente
    @Transactional
    public void baixarEstoque(Long produtoId, Integer quantidade) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new BusinessException("Produto não encontrado ID: " + produtoId));

        // Importante: A model Produto deve lançar exceção se a quantidade for maior que o saldo
        produto.removerEstoque(quantidade);

        produtoRepository.save(produto);
    }

    // Mantém este para outras operações que venham via API/Controller
    @Transactional
    public void darSaida(MovimentacaoEstoqueDTO dto) {
        baixarEstoque(dto.produtoId(), dto.quantidade());
    }

    @Transactional
    public void darEntrada(MovimentacaoEstoqueDTO dto) {
        Produto produto = produtoRepository.findById(dto.produtoId())
                .orElseThrow(() -> new BusinessException("Produto não encontrado"));
        produto.adicionarEstoque(dto.quantidade());
        produtoRepository.save(produto);
    }
}