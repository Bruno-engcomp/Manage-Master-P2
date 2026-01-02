package com.sigfe.backend.service;

import com.sigfe.backend.model.Compra;
import com.sigfe.backend.repository.CompraRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompraService {

    private final CompraRepository compraRepository;

    public CompraService(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

    public Compra salvar(Compra compra) {
        return compraRepository.save(compra);
    }

    public List<Compra> listarTodas() {
        return compraRepository.findAll();
    }

    public Compra buscarPorId(Long id) {
        return compraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra não encontrada"));
    }

    public Compra atualizar(Long id, Compra compraAtualizada) {
        Compra compra = buscarPorId(id);

        compra.setItens(compraAtualizada.getItens());
        compra.setUsuario(compraAtualizada.getUsuario());
        compra.setFormaPagamento(compraAtualizada.getFormaPagamento());
        compra.setFornecedor(compraAtualizada.getFornecedor());

        return compraRepository.save(compra);
    }

    public void marcarComoPaga(Long id) {
        Compra compra = buscarPorId(id);
        compra.marcarComoPaga();
        compraRepository.save(compra);
    }

    public void cancelar(Long id) {
        Compra compra = buscarPorId(id);
        compra.cancelar();
        compraRepository.save(compra);
    }

    public void deletar(Long id) {
        compraRepository.deleteById(id);
    }
}
