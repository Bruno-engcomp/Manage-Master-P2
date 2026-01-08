package com.sigfe.backend.service;

import com.sigfe.backend.dto.fornecedor.FornecedorCreateDTO;
import com.sigfe.backend.dto.fornecedor.FornecedorResponseDTO;
import com.sigfe.backend.exception.BusinessException;
import com.sigfe.backend.model.Fornecedor;
import com.sigfe.backend.repository.FornecedorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FornecedorService {

    private final FornecedorRepository repository;

    public FornecedorService(FornecedorRepository repository) {
        this.repository = repository;
    }

    // CREATE
    @Transactional
    public FornecedorResponseDTO salvar(FornecedorCreateDTO dto) {
        // CONVERSÃO: DTO -> Entidade
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(dto.nome());
        fornecedor.setEmail(dto.email());
        fornecedor.setTelefone(dto.telefone());

        Fornecedor salvo = repository.save(fornecedor);

        // RETORNO: Converte a Entidade salva de volta para DTO
        return new FornecedorResponseDTO(salvo);
    }

    // READ - LISTAR TODOS
    public List<FornecedorResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(FornecedorResponseDTO::new)
                .toList();
    }

    // READ - BUSCAR POR ID
    public FornecedorResponseDTO buscarPorId(Long id) {
        Fornecedor fornecedor = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Fornecedor não encontrado"));

        return new FornecedorResponseDTO(fornecedor);
    }

    // DELETE
    @Transactional
    public void remover(Long id) {
        if (!repository.existsById(id)) {
            throw new BusinessException("Fornecedor não encontrado");
        }
        repository.deleteById(id);
    }
}

/*
 * CRIACAO de camada Service para a entidade produto
 * Implementacao de metodos salva, listar, buscar por Id e remover produtos
 * O Service é o intermediário que organiza o fluxo: ele pega os dados que o Controller recebeu,
 *  aplica as regras necessárias e manda o Repository salvar.*/
