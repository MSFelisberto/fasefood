package br.com.fiap.fasefood.infra.controllers.mapper.cardapio;

import br.com.fiap.fasefood.application.usecases.cardapio.atualizar.AtualizarCardapioItemBatchInput;
import br.com.fiap.fasefood.application.usecases.cardapio.atualizar.AtualizarCardapioItemInput;
import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioItemsBatchInput;
import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioItemInput;
import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioItemOutput;
import br.com.fiap.fasefood.application.usecases.cardapio.criar.ItemCardapioInput;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PageOutput;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PaginationInput;
import br.com.fiap.fasefood.core.entities.Cardapio;
import br.com.fiap.fasefood.core.entities.CardapioItem;

import br.com.fiap.fasefood.infra.controllers.dto.cardapio.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CardapioItemMapper {

    public static CardapioItem toDomain(CreateCardapioItemDTO dto, Cardapio cardapio) {
        return CardapioItem.create(null, cardapio, dto.nome(), dto.descricao(), dto.preco(), dto.apenasNoLocal(), dto.caminhoFoto(), true);
    }

    public static CardapioItem toDomain(ItensCreateCardapioItemDTO dto, Cardapio cardapio) {
        return CardapioItem.create(null, cardapio, dto.nome(), dto.descricao(), dto.preco(), dto.apenasNoLocal(), dto.caminhoFoto(), true);
    }

    public static CardapioItem toDomain(ItemCardapioInput input, Cardapio cardapio) {
        if (input == null || cardapio == null) {
            return null;
        }

        return CardapioItem.create(
                null,
                cardapio,
                input.nome(),
                input.descricao(),
                input.preco(),
                input.apenasNoLocal(),
                input.caminhoFoto(),
                true
        );
    }

    public static List<CriarCardapioItemInput> toCriarCardapioItemInput(List<ItensCreateCardapioItemDTO> itensCardapioDTO) {
        List<CriarCardapioItemInput> criarCardapioItemInputs = new ArrayList<>();

        for (ItensCreateCardapioItemDTO item : itensCardapioDTO) {
            criarCardapioItemInputs.add(
                    new CriarCardapioItemInput(
                            null,
                            item.nome(),
                            item.descricao(),
                            item.preco(),
                            item.apenasNoLocal(),
                            item.caminhoFoto()
                    )
            );
        }

        return criarCardapioItemInputs;
    }

    public static CriarCardapioItemInput toCriarCardapioItemInput(CreateCardapioItemDTO dto) {
        return new CriarCardapioItemInput(
                dto.cardapioId(),
                dto.nome(),
                dto.descricao(),
                dto.preco(),
                dto.apenasNoLocal(),
                dto.caminhoFoto()
        );
    }

    public static CardapioItemResponseDTO toCardapioItemResponseDTO(CriarCardapioItemOutput item) {
        return new CardapioItemResponseDTO(
                item.id(),
                item.cardapioId(),
                item.nome(),
                item.descricao(),
                item.preco(),
                item.apenasNoLocal(),
                item.caminhoFoto()
        );
    }

    public static CriarCardapioItemOutput toCriarCardapioItemOutput(CardapioItem item) {
        if (item == null || item.getCardapio() == null) {
            return null;
        }

        return new CriarCardapioItemOutput(
                item.getId(),
                item.getCardapio().getId(),
                item.getNome(),
                item.getDescricao(),
                item.getPreco(),
                item.isApenasNoLocal(),
                item.getCaminhoFoto()
        );
    }

    public static List<CardapioItemResponseDTO> toResponseDTOList(List<CriarCardapioItemOutput> outputs) {
        return outputs.stream()
                .map(CardapioItemMapper::toCardapioItemResponseDTO)
                .collect(Collectors.toList());
    }

    public static CriarCardapioItemsBatchInput toCriarCardapioItemsBatchInput(CreateCardapioItemsBatchDTO dto) {
        if (dto == null) {
            return null;
        }

        List<ItemCardapioInput> itemInputs = dto.itens().stream()
                .map(itemDto -> new ItemCardapioInput(
                        itemDto.nome(),
                        itemDto.descricao(),
                        itemDto.preco(),
                        itemDto.apenasNoLocal(),
                        itemDto.caminhoFoto()
                ))
                .collect(Collectors.toList());

        return new CriarCardapioItemsBatchInput(dto.cardapioId(), itemInputs);
    }

    public static Page<CardapioItemResponseDTO> toCardapioItemResponseDTOPage(PageOutput<CriarCardapioItemOutput> outputPage) {
        List<CardapioItemResponseDTO> dtos = outputPage.content().stream()
                .map(CardapioItemMapper::toCardapioItemResponseDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, PageRequest.of(outputPage.currentPage(), outputPage.size()), outputPage.totalElements());
    }

    public static AtualizarCardapioItemInput toAtualizarCardapioItemInput(UpdateCardapioItemDTO dto) {
        return new AtualizarCardapioItemInput(
                dto.nome(),
                dto.descricao(),
                dto.preco(),
                dto.apenasNoLocal(),
                dto.caminhoFoto()
        );
    }

    public static List<AtualizarCardapioItemBatchInput> toAtualizarCardapioItemBatchInputList(UpdateCardapioItemsBatchDTO dto) {
        return dto.itens().stream()
                .map(itemDto -> new AtualizarCardapioItemBatchInput(
                        itemDto.id(),
                        itemDto.nome(),
                        itemDto.descricao(),
                        itemDto.preco(),
                        itemDto.apenasNoLocal(),
                        itemDto.caminhoFoto()
                ))
                .collect(Collectors.toList());
    }

    public static PaginationInput toPaginationInput(Pageable pageable) {
        if (pageable == null) {
            return new PaginationInput(0, 10, "id", "ASC");
        }

        String sortField = "id";
        String sortDirection = "ASC";

        if (pageable.getSort().isSorted()) {
            var order = pageable.getSort().iterator().next();
            sortField = order.getProperty();
            sortDirection = order.getDirection().name();
        }

        return new PaginationInput(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                sortField,
                sortDirection
        );
    }
}