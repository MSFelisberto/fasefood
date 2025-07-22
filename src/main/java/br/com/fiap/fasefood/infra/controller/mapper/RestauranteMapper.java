package br.com.fiap.fasefood.infra.controller.mapper;

import br.com.fiap.fasefood.core.domain.entities.restaurante.Restaurante;
import br.com.fiap.fasefood.infra.persistence.entities.RestauranteEntity;

public class RestauranteMapper {
    public static Restaurante toDomain(RestauranteEntity restaurante) {
        return Restaurante.createRestaurante(
                restaurante.getNome(),
                EnderecoEntityMapper.toDomain(restaurante.getEndereco()),
                restaurante.getTipoCozinha(),
                restaurante.getHorarioFuncionamento(),
                UsuarioMapper.toDomain(restaurante.getUsuario())
        );
    }
}