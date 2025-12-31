package com.sigfe.backend.service;

import com.sigfe.backend.model.Produto;
import com.sigfe.backend.repository.ProdutoRepository;

/*Anotacao que mostra que esta classe e service do spring
* Services concentram a logica  de negocio da aplicacao*/
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Marca uma classe como componente service gerenciado pelo Spring
public class ProdutoService {


    // Referência ao repositório de Produto
    // O Service usa o Repository para acessar o banco
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;  // O spring injeta automaticamente o ProdutoRepository aqui
    }

    public Produto salvar(Produto produto)
    {
        return produtoRepository.save(produto); // Responsavel por salvar ou atualizar um produto
    }

    public List<Produto> listarTodos()
    {
        return produtoRepository.findAll(); // Retorna uma lista com todos os produtos cadastrados no banco
    }

    public Optional<Produto> buscarPorId (Long id)
    {
        return produtoRepository.findById(id); // Busca um produto pelo Id
    }

    public void remover(Long id)
    {
        produtoRepository.deleteById(id);  // Remove um produto do banco usando o Id
    }
}

/*
* CRIACAO de camada Service para a entidade produto
* Implementacao de metodos salva, listar, buscar por Id e remover produtos
* O Service é o intermediário que organiza o fluxo: ele pega os dados que o Controller recebeu,
*  aplica as regras necessárias e manda o Repository salvar.*/