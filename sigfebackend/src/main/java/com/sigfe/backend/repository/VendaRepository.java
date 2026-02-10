package com.sigfe.backend.repository;

import com.sigfe.backend.model.Venda;
import com.sigfe.backend.model.enums.StatusVenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VendaRepository extends JpaRepository <Venda, Long> {

    List<Venda> findByDataTransacaoBetweenAndStatus (LocalDate inicio,
                                            LocalDate fim,
                                            StatusVenda status);

}

/*No Spring Boot, um Repository (Repositório) é uma camada
de abstração que serve para gerenciar o acesso aos dados.
Ele funciona como uma "ponte" entre
na aplicação e no banco de dados.*/