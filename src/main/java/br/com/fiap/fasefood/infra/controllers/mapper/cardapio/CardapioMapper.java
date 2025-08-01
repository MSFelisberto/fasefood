package br.com.fiap.fasefood.infra.controllers.mapper.cardapio;

import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioInput;
import br.com.fiap.fasefood.application.usecases.cardapio.listar.CardapioResponseOutput;
import br.com.fiap.fasefood.core.entities.Cardapio;
import br.com.fiap.fasefood.core.entities.Restaurante;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CardapioResponseDTO;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CreateCardapioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public class CardapioMapper {
    public static Cardapio toDomain(CreateCardapioDTO dto, Restaurante restaurante) {
        return Cardapio.create(null, restaurante, dto.nome(), dto.descricao(), true);
    }

    public static CardapioResponseDTO toResponseDTO(Cardapio cardapio) {
        return new CardapioResponseDTO(cardapio.getId(), cardapio.getRestaurante().getId(), cardapio.getNome(), cardapio.getDescricao());
    }
    public static CardapioResponseDTO toResponseDTO(CardapioResponseOutput cardapioResponseOutput) {
        return new CardapioResponseDTO(
                cardapioResponseOutput.id(),
                cardapioResponseOutput.restauranteId(),
                cardapioResponseOutput.nome(),
                cardapioResponseOutput.descricao()
        );
    }

    public static CriarCardapioInput toCriarCardapioInput(CreateCardapioDTO dto) {
        return new CriarCardapioInput(
                dto.restauranteId(),
                dto.nome(),
                dto.descricao(),
                CardapioItemMapper.toCriarCardapioItemInput(dto.itens())
        );
    }

    public static Page<CardapioResponseDTO> toRestauranteDtoPaginacao(Page<CardapioResponseOutput> cardapioResponseOutput) {
        List<CardapioResponseDTO> dtos = cardapioResponseOutput
                .stream()
                .map(CardapioMapper::toResponseDTO)
                .toList();

        return new PageImpl<>(
                dtos,
                cardapioResponseOutput.getPageable(),
                cardapioResponseOutput.getTotalElements()
        );
    }
}