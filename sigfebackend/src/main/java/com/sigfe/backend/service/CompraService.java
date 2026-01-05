package com.sigfe.backend.service;

import com.sigfe.backend.dto.compra.CompraCreateDTO;
import com.sigfe.backend.exception.BusinessException;
import com.sigfe.backend.model.Compra;
import com.sigfe.backend.repository.CompraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CompraService {

    private final CompraRepository compraRepository;

    public CompraService(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

    @Transactional
    public Compra salvar(CompraCreateDTO dto) {
        // CONVERSÃO MANUAL: DTO -> Entidade
        Compra compra = new Compra();
        compra.setUsuario(dto.usuario());
        compra.setNumeroDocumento(dto.numeroDocumento());
        compra.setValorTotal(dto.valorTotal());
        // Se houver lógica de fornecedor, busque o objeto fornecedor aqui

        return compraRepository.save(compra);
    }

    public List<Compra> listarTodas() {
        return compraRepository.findAll();
    }

    public Compra buscarPorId(Long id) {
        return compraRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Compra não encontrada"));
    }
}