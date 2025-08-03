package br.com.fiap.fasefood.infra.controllers.mapper.cardapio;

import br.com.fiap.fasefood.core.entities.Cardapio;
import br.com.fiap.fasefood.infra.controllers.mapper.restaurante.RestauranteEntityMapper;
import br.com.fiap.fasefood.infra.persistence.entities.CardapioEntity;

public class CardapioEntityMapper {
    public static Cardapio toDomain(CardapioEntity entity) {
        if (entity == null) return null;
        return Cardapio.create(entity.getId(), RestauranteEntityMapper.toDomain(entity.getRestaurante()), entity.getNome(), entity.getDescricao(), entity.isAtivo());
    }

    public static CardapioEntity toEntity(Cardapio domain) {
        if (domain == null) return null;
        return new CardapioEntity(domain.getId(), RestauranteEntityMapper.toEntity(domain.getRestaurante()), domain.getNome(), domain.getDescricao(), domain.isAtivo());
    }
}