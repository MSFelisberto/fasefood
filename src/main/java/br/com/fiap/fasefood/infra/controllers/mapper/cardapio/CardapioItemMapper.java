package br.com.fiap.fasefood.infra.controllers.mapper.cardapio;

import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioItemInput;
import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioItemOutput;
import br.com.fiap.fasefood.core.entities.Cardapio;
import br.com.fiap.fasefood.core.entities.CardapioItem;

import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CardapioItemResponseDTO;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CreateCardapioItemDTO;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.ItensCreateCardapioItemDTO;

import java.util.ArrayList;
import java.util.List;

public class CardapioItemMapper {

    public static CardapioItem toDomain(CreateCardapioItemDTO dto, Cardapio cardapio) {
        return CardapioItem.create(null, cardapio, dto.nome(), dto.descricao(), dto.preco(), dto.apenasNoLocal(), dto.caminhoFoto(), true);
    }

    public static CardapioItem toDomain(ItensCreateCardapioItemDTO dto, Cardapio cardapio) {
        return CardapioItem.create(null, cardapio, dto.nome(), dto.descricao(), dto.preco(), dto.apenasNoLocal(), dto.caminhoFoto(), true);
    }

    public static CardapioItemResponseDTO toResponseDTO(CardapioItem item) {
        if (item == null || item.getCardapio() == null) {
            return null;
        }
        return new CardapioItemResponseDTO(
                item.getId(),
                item.getCardapio().getId(),
                item.getNome(),
                item.getDescricao(),
                item.getPreco(),
                item.isApenasNoLocal(),
                item.getCaminhoFoto()
        );
    }

    public static List<CriarCardapioItemInput> toCriarCardapioItemInput(List<ItensCreateCardapioItemDTO> itensCardapioDTO) {
        List<CriarCardapioItemInput> criarCardapioItemInputs = new ArrayList<>();

        for(ItensCreateCardapioItemDTO item : itensCardapioDTO) {
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
}