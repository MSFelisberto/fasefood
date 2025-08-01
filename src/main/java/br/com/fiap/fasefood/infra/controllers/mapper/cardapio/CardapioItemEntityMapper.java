package br.com.fiap.fasefood.infra.controllers.mapper.cardapio;

import br.com.fiap.fasefood.core.entities.CardapioItem;
import br.com.fiap.fasefood.infra.persistence.entities.CardapioItemEntity;

public class CardapioItemEntityMapper {

    public static CardapioItem toDomain(CardapioItemEntity entity) {
        if (entity == null) return null;
        return CardapioItem.create(entity.getId(), CardapioEntityMapper.toDomain(entity.getCardapio()), entity.getNome(), entity.getDescricao(), entity.getPreco(), entity.isApenasNoLocal(), entity.getCaminhoFoto(), entity.isAtivo());
    }

    public static CardapioItemEntity toEntity(CardapioItem domain) {
        if (domain == null) return null;
        return new CardapioItemEntity(domain.getId(), CardapioEntityMapper.toEntity(domain.getCardapio()), domain.getNome(), domain.getDescricao(), domain.getPreco(), domain.isApenasNoLocal(), domain.getCaminhoFoto(), domain.isAtivo());
    }
}