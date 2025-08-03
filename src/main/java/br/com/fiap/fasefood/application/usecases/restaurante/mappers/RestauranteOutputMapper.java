package br.com.fiap.fasefood.application.usecases.restaurante.mappers;

import br.com.fiap.fasefood.application.usecases.restaurante.RestauranteOutput;
import br.com.fiap.fasefood.application.usecases.usuario.mappers.UsuarioOutputMapper;
import br.com.fiap.fasefood.core.entities.Restaurante;

public class RestauranteOutputMapper {
    public static RestauranteOutput toRestauranteOutput(Restaurante restaurante) {
        return new RestauranteOutput(
                restaurante.getId(),
                restaurante.getNome(),
                restaurante.getEndereco(),
                restaurante.getTipoCozinha(),
                restaurante.getHorarioAbertura(),
                restaurante.getHorarioFechamento(),
                UsuarioOutputMapper.toUsuarioOutput(
                        restaurante.getDono()
                )
        );
    }
}
