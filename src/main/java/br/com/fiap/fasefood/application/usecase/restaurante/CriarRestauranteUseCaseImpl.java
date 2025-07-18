package br.com.fiap.fasefood.application.usecase.restaurante;

import br.com.fiap.fasefood.core.domain.entities.restaurante.Restaurante;
import br.com.fiap.fasefood.core.usecase.gateways.RestauranteRepository;
import br.com.fiap.fasefood.core.usecase.interfaces.restaurante.CriarRestauranteUseCase;

public class CriarRestauranteUseCaseImpl implements CriarRestauranteUseCase {
    private final RestauranteRepository restauranteRepository;

    public CriarRestauranteUseCaseImpl(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    @Override
    public Restaurante criarRestaurante(Restaurante restaurante) {
        return this.restauranteRepository.create(restaurante);
    }
}
