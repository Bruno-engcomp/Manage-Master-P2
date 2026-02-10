package com.sigfe.backend.service;

import com.sigfe.backend.dto.produto.ProdutoCreateDTO;
import com.sigfe.backend.exception.BusinessException;
import com.sigfe.backend.model.Categoria;
import com.sigfe.backend.model.Fornecedor;
import com.sigfe.backend.model.Produto;
import com.sigfe.backend.repository.CategoriaRepository;
import com.sigfe.backend.repository.FornecedorRepository;
import com.sigfe.backend.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final FornecedorRepository fornecedorRepository;

    @Transactional
    public Produto salvar(ProdutoCreateDTO dto) {
        Produto produto = new Produto();
        produto.setNome(dto.nome());
        produto.setMarca(dto.marca());
        produto.setPreco(dto.preco());
        produto.setQuantidade(dto.quantidade());
        produto.setLimit(dto.limit());
        produto.setValidade(dto.validade());


        // Busca Categoria
        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com ID: " + dto.categoriaId()));
        produto.setCategoria(categoria);

        // Busca Fornecedor (O QUE ESTAVA FALTANDO)
        Fornecedor fornecedor = fornecedorRepository.findById(dto.fornecedorId())
                .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado com ID: " + dto.fornecedorId()));
        produto.setFornecedor(fornecedor);

        return produtoRepository.save(produto);
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public Produto buscarPorNome(String nome) {
        return produtoRepository.findByNomeIgnoreCase(nome)
                .orElseThrow(() -> new BusinessException("Produto não encontrado com o nome: " + nome));
    }

    @Transactional
    public void remover(Long id) {
        produtoRepository.deleteById(id);
    }
}