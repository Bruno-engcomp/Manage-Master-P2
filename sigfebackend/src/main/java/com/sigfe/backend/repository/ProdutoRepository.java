package com.sigfe.backend.repository;

import com.sigfe.backend.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByQuantidadeLessThan(int quantidade);
    List<Produto> findByQuantidadeLessThanEqual(int quantidade);
    Optional<Produto> findByNomeIgnoreCase(String nome);


}

/*No Spring Boot, um Repository (Repositório) é uma camada
de abstração que serve para gerenciar o acesso aos dados.
Ele funciona como uma "ponte" entre
na aplicação e no banco de dados.*/
