package com.sigfe.backend.service;

import com.sigfe.backend.dto.Item.ItemCompraDTO;
import com.sigfe.backend.dto.compra.CompraCreateDTO;
import com.sigfe.backend.exception.BusinessException;
import com.sigfe.backend.model.Compra;
import com.sigfe.backend.model.Fornecedor;
import com.sigfe.backend.model.ItemTransacao;
import com.sigfe.backend.model.Produto;
import com.sigfe.backend.model.enums.FormaPagamento;
import com.sigfe.backend.repository.CompraRepository;
import com.sigfe.backend.repository.FornecedorRepository;
import com.sigfe.backend.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CompraService {

    private final CompraRepository compraRepository;
    private final ProdutoRepository produtoRepository;
    private final FornecedorRepository fornecedorRepository;

    public CompraService(
            CompraRepository compraRepository,
            ProdutoRepository produtoRepository,
            FornecedorRepository fornecedorRepository
    ) {
        this.compraRepository = compraRepository;
        this.produtoRepository = produtoRepository;
        this.fornecedorRepository = fornecedorRepository;
    }

    @Transactional
    public Compra salvar(CompraCreateDTO dto) {

        if (dto.itens() == null || dto.itens().isEmpty()) {
            throw new BusinessException("A compra deve possuir ao menos um item");
        }

        Compra compra = new Compra();
        compra.setUsuario(dto.usuario());
        compra.setNumeroDocumento(dto.numeroDocumento());

        // String -> Enum
        try {
            compra.setFormaPagamento(
                    FormaPagamento.valueOf(dto.formaPagamento().toUpperCase())
            );
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Forma de pagamento inválida");
        }

        // Fornecedor
        Fornecedor fornecedor = fornecedorRepository.findById(dto.fornecedorId())
                .orElseThrow(() -> new BusinessException("Fornecedor não encontrado"));
        compra.setFornecedor(fornecedor);

        List<ItemTransacao> itens = new ArrayList<>();
        BigDecimal valorTotal = BigDecimal.ZERO;

        for (ItemCompraDTO itemDTO : dto.itens()) {

            Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                    .orElseThrow(() ->
                            new BusinessException("Produto não encontrado: ID " + itemDTO.getProdutoId())
                    );

            if (itemDTO.getQuantidade() <= 0) {
                throw new BusinessException("Quantidade inválida para o produto " + produto.getNome());
            }

            if (itemDTO.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
                throw new BusinessException("Preço inválido para o produto " + produto.getNome());
            }

            ItemTransacao item = new ItemTransacao();
            item.setProduto(produto);
            item.setQuantidade(itemDTO.getQuantidade());
            item.setPreco(itemDTO.getPreco());
            item.setTransacao(compra);


            BigDecimal subtotal = itemDTO.getPreco()
                    .multiply(BigDecimal.valueOf(itemDTO.getQuantidade()));

            valorTotal = valorTotal.add(subtotal);

            itens.add(item);
        }

        compra.setItens(itens);

        compra.setValorTotal(valorTotal);

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
