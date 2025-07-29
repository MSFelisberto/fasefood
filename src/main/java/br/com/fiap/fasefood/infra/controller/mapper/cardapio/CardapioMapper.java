package br.com.fiap.fasefood.infra.controller.mapper.cardapio;

import br.com.fiap.fasefood.core.domain.entities.Cardapio;
import br.com.fiap.fasefood.core.domain.entities.Restaurante;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CardapioResponseDTO;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CreateCardapioDTO;

public class CardapioMapper {
    public static Cardapio toDomain(CreateCardapioDTO dto, Restaurante restaurante) {
        return new Cardapio(null, restaurante, dto.nome(), dto.descricao(), true);
    }

    public static CardapioResponseDTO toResponseDTO(Cardapio cardapio) {
        return new CardapioResponseDTO(cardapio.getId(), cardapio.getRestaurante().getId(), cardapio.getNome(), cardapio.getDescricao());
    }
}