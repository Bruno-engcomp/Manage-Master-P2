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

    // 🔹 ENTRADA DE ESTOQUE (COMPRA)
    @Transactional
    public void darEntrada(MovimentacaoEstoqueDTO dto) {

        Produto produto = produtoRepository.findById(dto.produtoId())
                .orElseThrow(() -> new BusinessException("Produto não encontrado"));

        produto.adicionarEstoque(dto.quantidade());

        produtoRepository.save(produto);
    }

    // 🔹 SAÍDA DE ESTOQUE (VENDA)
    @Transactional
    public void darSaida(MovimentacaoEstoqueDTO dto) {

        Produto produto = produtoRepository.findById(dto.produtoId())
                .orElseThrow(() -> new BusinessException("Produto não encontrado"));

        produto.removerEstoque(dto.quantidade());

        produtoRepository.save(produto);
    }
}
