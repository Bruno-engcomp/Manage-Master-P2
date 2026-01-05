package com.sigfe.backend.service;

import com.sigfe.backend.dto.Item.ItemVendaCreateDTO;
import com.sigfe.backend.dto.venda.VendaCreateDTO;
import com.sigfe.backend.exception.BusinessException;
import com.sigfe.backend.model.ItemTransacao;
import com.sigfe.backend.model.Produto;
import com.sigfe.backend.model.Venda;
import com.sigfe.backend.model.enums.FormaPagamento;
import com.sigfe.backend.repository.ProdutoRepository;
import com.sigfe.backend.repository.VendaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;
    private final EstoqueService estoqueService;

    public VendaService(
            VendaRepository vendaRepository,
            ProdutoRepository produtoRepository,
            EstoqueService estoqueService) {

        this.vendaRepository = vendaRepository;
        this.produtoRepository = produtoRepository;
        this.estoqueService = estoqueService;
    }

    @Transactional
    public Venda salvar(VendaCreateDTO dto) {

        Venda venda = new Venda();
        venda.setFormaPagamento(FormaPagamento.valueOf(dto.formaPagamento()));
        venda.setNumeroDocumento(dto.numeroDocumento());

        dto.itens().forEach(itemDTO -> {

            Produto produto = produtoRepository.findById(itemDTO.produtoId())
                    .orElseThrow(() -> new BusinessException("Produto não encontrado"));

            ItemTransacao item = new ItemTransacao();
            item.setProduto(produto);
            item.setQuantidade(itemDTO.quantidade());
            item.setPreco(itemDTO.preco());
            item.setTransacao(venda);

            venda.getItens().add(item);

            // 🔥 baixa de estoque obrigatória
            estoqueService.darSaida(
                    new com.sigfe.backend.dto.estoque.MovimentacaoEstoqueDTO(
                            produto.getId(),
                            itemDTO.quantidade()
                    )
            );
        });

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
