package br.com.fiap.fasefood.infra.controller.mapper.restaurante;

import br.com.fiap.fasefood.core.domain.entities.restaurante.Restaurante;
import br.com.fiap.fasefood.infra.controller.mapper.EnderecoEntityMapper;
import br.com.fiap.fasefood.infra.controller.mapper.UserEntityMapper;
import br.com.fiap.fasefood.infra.persistence.entities.RestauranteEntity;

public class RestauranteEntityMapper {

    public static Restaurante toDomain(RestauranteEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Restaurante(
                entity.getId(),
                entity.getNome(),
                EnderecoEntityMapper.toDomain(entity.getEndereco()),
                entity.getTipoCozinha(),
                entity.getHorarioAbertura(),
                entity.getHorarioFechamento(),
                UserEntityMapper.toDomain(entity.getDono()),
                entity.isAtivo()
        );
    }

    public static RestauranteEntity toEntity(Restaurante domain) {
        if (domain == null) {
            return null;
        }
        return new RestauranteEntity(
                domain.getId(),
                domain.getNome(),
                EnderecoEntityMapper.toEntity(domain.getEndereco()),
                domain.getTipoCozinha(),
                domain.getHorarioAbertura(),
                domain.getHorarioFechamento(),
                UserEntityMapper.toEntity(domain.getDono()),
                domain.isAtivo()
        );
    }
}