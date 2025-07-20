package br.com.fiap.fasefood.infra.persistence;

import br.com.fiap.fasefood.core.domain.entities.restaurante.Restaurante;
import br.com.fiap.fasefood.core.usecase.gateways.RestauranteRepository;
import br.com.fiap.fasefood.infra.persistence.entities.EnderecoEntity;
import br.com.fiap.fasefood.infra.persistence.entities.RestauranteEntity;
import br.com.fiap.fasefood.infra.persistence.entities.TipoUsuarioEntity;
import br.com.fiap.fasefood.infra.persistence.entities.UsuarioEntity;
import br.com.fiap.fasefood.infra.persistence.jpa.RestauranteJpaRepository;

public class RestauranteRespositoryImpl implements RestauranteRepository {

    private final RestauranteJpaRepository restauranteJpaRepository;

    public RestauranteRespositoryImpl(RestauranteJpaRepository restauranteJpaRepository) {
        this.restauranteJpaRepository = restauranteJpaRepository;
    }

    @Override
    public Restaurante create(Restaurante restaurante) {

        RestauranteEntity restauranteEntity = this.restauranteJpaRepository.save(
                new RestauranteEntity(
                        restaurante.getNome(),
                        new EnderecoEntity(restaurante.getEndereco()),
                        restaurante.getTipoCozinha(),
                        restaurante.getHorarioFuncionamento(),
                        new UsuarioEntity(
                                restaurante.getUsuario().getId(),
                                restaurante.getUsuario().getNome(),
                                restaurante.getUsuario().getEmail(),
                                restaurante.getUsuario().getLogin(),
                                restaurante.getUsuario().getSenha(),
                                restaurante.getUsuario().getDataUltimaAtualizacao(),
                                new TipoUsuarioEntity(
                                        restaurante.getUsuario().getTipoUsuario().getId(),
                                        restaurante.getUsuario().getTipoUsuario().getNome()
                                ),
                                new EnderecoEntity(restaurante.getUsuario().getEndereco()),
                                restaurante.getUsuario().isAtivo()
                        )
                )
        );

        restaurante.setId(restauranteEntity.getId());
        return restaurante;
    }
}
