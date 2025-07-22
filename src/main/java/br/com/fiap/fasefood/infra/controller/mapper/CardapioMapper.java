package br.com.fiap.fasefood.infra.controller.mapper;

import br.com.fiap.fasefood.core.domain.entities.Cardapio;
import br.com.fiap.fasefood.infra.controller.dto.CreateCardapioDTO;
import br.com.fiap.fasefood.infra.persistence.entities.CardapioEntity;

import java.time.LocalDate;

public class CardapioMapper {

    public static Cardapio toDomain(CardapioEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Cardapio(
                entity.getId(),
                entity.getNome(),
                entity.getDescricao(),
                entity.getDataCriacao(),
                entity.getDataUltimoUpdate(),
                ItemCardapioMapper.toDomain(entity.getItensCardapio())
        );
    }
    public static Cardapio toDomain(CreateCardapioDTO cardapioDTO) {
        if (cardapioDTO == null) {
            return null;
        }

        return new Cardapio(
                cardapioDTO.nome(),
                cardapioDTO.descricao(),
                LocalDate.now(),
                LocalDate.now(),
                ItemCardapioMapper.dtoToDomain(cardapioDTO.itemCardapioDTO())
        );
    }

    public static CardapioEntity toEntity(Cardapio cardapio) {
        if (cardapio == null) {
            return null;
        }

        return new CardapioEntity(
                cardapio.getId(),
                cardapio.getNome(),
                cardapio.getDescricao(),
                cardapio.getDataCriacao(),
                cardapio.getDataUltimoUpdate(),
                ItemCardapioMapper.toEntity(cardapio.getItensCardapio())
        );
    }
}
