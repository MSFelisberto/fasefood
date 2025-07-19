package br.com.fiap.fasefood.core.usecase.restaurante;

import br.com.fiap.fasefood.core.domain.entities.restaurante.Restaurante;
import br.com.fiap.fasefood.core.usecase.gateways.RestauranteRepository;

public class CriarRestauranteUseCase {
    private final RestauranteRepository restauranteRepository;

    public CriarRestauranteUseCase(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    public Restaurante execute(Restaurante restaurante) {
        return this.restauranteRepository.create(restaurante);
    }
}
