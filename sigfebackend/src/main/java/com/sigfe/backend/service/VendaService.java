package com.sigfe.backend.service;

import com.sigfe.backend.model.Venda;
import com.sigfe.backend.repository.VendaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;

    public VendaService(VendaRepository vendaRepository) {
        this.vendaRepository = vendaRepository;
    }

    public Venda salvar(Venda venda) {
        return vendaRepository.save(venda);
    }

    public List<Venda> listarTodas() {
        return vendaRepository.findAll();
    }

    public Venda buscarPorId(Long id) {
        return vendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada"));
    }

    public Venda atualizar(Long id, Venda vendaAtualizada) {
        Venda venda = buscarPorId(id);

        venda.setFornecedor(vendaAtualizada.getFornecedor());
        venda.setFormaPagamento(vendaAtualizada.getFormaPagamento());
        venda.setNumeroDocumento(vendaAtualizada.getNumeroDocumento());
        venda.setStatus(vendaAtualizada.getStatus());
        venda.setItens(vendaAtualizada.getItens());

        return vendaRepository.save(venda);
    }

    public void deletar(Long id) {
        vendaRepository.deleteById(id);
    }
}

/*
 * CRIACAO de camada Service para a entidade produto
 * Implementacao de metodos salva, listar, buscar por Id e remover produtos
 * O Service é o intermediário que organiza o fluxo: ele pega os dados que o Controller recebeu,
 *  aplica as regras necessárias e manda o Repository salvar.*/
