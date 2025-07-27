package br.com.fiap.fasefood.infra.controller.mapper.cardapio;

import br.com.fiap.fasefood.core.domain.entities.Cardapio;
import br.com.fiap.fasefood.core.domain.entities.CardapioItem;

import br.com.fiap.fasefood.infra.controller.dto.cardapio.CardapioItemResponseDTO;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CreateCardapioItemDTO;

public class CardapioItemMapper {

    public static CardapioItem toDomain(CreateCardapioItemDTO dto, Cardapio cardapio) {
        return new CardapioItem(null, cardapio, dto.nome(), dto.descricao(), dto.preco(), dto.apenasNoLocal(), dto.caminhoFoto(), true); // <-- ALTERADO
    }

    public static CardapioItemResponseDTO toResponseDTO(CardapioItem item) {
        return new CardapioItemResponseDTO(item.getId(), item.getCardapio().getId(), item.getNome(), item.getDescricao(), item.getPreco(), item.isApenasNoLocal(), item.getCaminhoFoto());
    }
}