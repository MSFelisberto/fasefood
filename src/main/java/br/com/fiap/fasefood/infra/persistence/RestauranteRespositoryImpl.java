package br.com.fiap.fasefood.infra.persistence;

import br.com.fiap.fasefood.core.domain.entities.restaurante.Restaurante;
import br.com.fiap.fasefood.core.usecase.gateways.RestauranteRepository;
import br.com.fiap.fasefood.infra.persistence.jpa.RestauranteJpaRepository;

public class RestauranteRespositoryImpl implements RestauranteRepository {

    private final RestauranteJpaRepository restauranteJpaRepository;

    public RestauranteRespositoryImpl(RestauranteJpaRepository restauranteJpaRepository) {
        this.restauranteJpaRepository = restauranteJpaRepository;
    }

    @Override
    public Restaurante create(Restaurante restaurante) {
        return null;
    }
}
