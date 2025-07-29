package br.com.fiap.fasefood.infra.controller.mapper.cardapio;

import br.com.fiap.fasefood.core.domain.entities.Cardapio;
import br.com.fiap.fasefood.infra.controller.mapper.restaurante.RestauranteEntityMapper;
import br.com.fiap.fasefood.infra.persistence.entities.CardapioEntity;

public class CardapioEntityMapper {
    public static Cardapio toDomain(CardapioEntity entity) {
        if (entity == null) return null;
        return new Cardapio(entity.getId(), RestauranteEntityMapper.toDomain(entity.getRestaurante()), entity.getNome(), entity.getDescricao(), entity.isAtivo());
    }

    public static CardapioEntity toEntity(Cardapio domain) {
        if (domain == null) return null;
        return new CardapioEntity(domain.getId(), RestauranteEntityMapper.toEntity(domain.getRestaurante()), domain.getNome(), domain.getDescricao(), domain.isAtivo());
    }
}