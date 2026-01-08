package com.sigfe.backend.service;

import com.sigfe.backend.dto.categoria.CategoriaCreateDTO;
import com.sigfe.backend.dto.categoria.CategoriaResponseDTO;
import com.sigfe.backend.exception.BusinessException;
import com.sigfe.backend.model.Categoria;
import com.sigfe.backend.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    //  CREATE
    public CategoriaResponseDTO salvar(CategoriaCreateDTO dto) {

        Categoria categoria = new Categoria();
        categoria.setNome(dto.nome());

        Categoria salva = repository.save(categoria);
        return new CategoriaResponseDTO(salva);
    }

    //  READ - LISTAR
    public List<CategoriaResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(CategoriaResponseDTO::new)
                .toList();
    }

    //  READ - BUSCAR POR ID
    public CategoriaResponseDTO buscarPorId(Long id) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Categoria não encontrada"));

        return new CategoriaResponseDTO(categoria);
    }

    //  UPDATE
    public CategoriaResponseDTO atualizar(Long id, CategoriaCreateDTO dto) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Categoria não encontrada"));

        categoria.setNome(dto.nome());

        Categoria atualizada = repository.save(categoria);
        return new CategoriaResponseDTO(atualizada);
    }

    //  DELETE
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new BusinessException("Categoria não encontrada");
        }
        repository.deleteById(id);
    }
}
