package com.sigfe.backend.service;

import com.sigfe.backend.dto.produto.ProdutoCreateDTO;
import com.sigfe.backend.dto.produto.ProdutoResponseDTO;
import com.sigfe.backend.exception.BusinessException;
import com.sigfe.backend.model.Categoria;
import com.sigfe.backend.model.Produto;
import com.sigfe.backend.repository.CategoriaRepository;
import com.sigfe.backend.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoService(ProdutoRepository repository, CategoriaRepository categoriaRepository) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public Produto salvar(ProdutoCreateDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new BusinessException("Categoria não encontrada"));

        Produto produto = new Produto(
                dto.nome(),
                dto.marca(),
                dto.preco(),
                dto.quantidade(),
                dto.validade(),
                categoria
        );

        return repository.save(produto);
    }

    public List<Produto> listarTodos() {
        return repository.findAll();
    }

    public Produto buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException("Produto não encontrado"));
    }

    @Transactional
    public void remover(Long id) {
        repository.deleteById(id);
    }
}




/*
* CRIACAO de camada Service para a entidade produto
* Implementacao de metodos salva, listar, buscar por Id e remover produtos
* O Service é o intermediário que organiza o fluxo: ele pega os dados que o Controller recebeu,
*  aplica as regras necessárias e manda o Repository salvar.*/