package com.sigfe.backend.service;

import com.sigfe.backend.dto.Item.ItemVendaCreateDTO;
import com.sigfe.backend.dto.venda.VendaCreateDTO;
import com.sigfe.backend.exception.BusinessException;
import com.sigfe.backend.model.*;
import com.sigfe.backend.model.enums.StatusVenda;
import com.sigfe.backend.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;
    private final FornecedorRepository fornecedorRepository;

    public VendaService(VendaRepository vendaRepository, ProdutoRepository produtoRepository, FornecedorRepository fornecedorRepository){
        this.vendaRepository = vendaRepository;
        this.produtoRepository = produtoRepository;
        this.fornecedorRepository = fornecedorRepository;
    }

    @Transactional
    public Venda salvar(VendaCreateDTO dto) {
        Venda venda = new Venda();


        if (dto.fornecedorId() != null) {
            Fornecedor f = fornecedorRepository.findById(dto.fornecedorId())
                    .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado"));
            venda.setFornecedor(f);
        }

        venda.setFormaPagamento(dto.formaPagamento());
        venda.setNumeroDocumento(dto.numeroDocumento());
        venda.setUsuario("Vendedor Master"); // Ou pegar do contexto de segurança

        List<ItemTransacao> itens = new ArrayList<>();
        for (ItemVendaCreateDTO itemDto : dto.itens()) {
            Produto produto = produtoRepository.findById(itemDto.produtoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            // ... lógica de baixar estoque ...

            ItemTransacao item = new ItemTransacao(produto, itemDto.quantidade(), itemDto.preco());
            item.setTransacao(venda);
            itens.add(item);
        }

        venda.setItens(itens);
        return vendaRepository.save(venda);
    }

    public Venda buscarPorId(Long id) {
        return vendaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Venda não encontrada"));
    }
}
/*
 * CRIACAO de camada Service para a entidade produto
 * Implementacao de metodos salva, listar, buscar por Id e remover produtos
 * O Service é o intermediário que organiza o fluxo: ele pega os dados que o Controller recebeu,
 *  aplica as regras necessárias e manda o Repository salvar.*/
