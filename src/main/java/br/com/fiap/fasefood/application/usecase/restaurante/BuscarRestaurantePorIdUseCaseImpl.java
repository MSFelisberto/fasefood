package br.com.fiap.fasefood.application.usecase.restaurante;

import br.com.fiap.fasefood.core.domain.entities.restaurante.Restaurante;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.RestauranteRepository;
import br.com.fiap.fasefood.core.usecase.interfaces.restaurante.BuscarRestaurantePorIdUseCase;
import org.springframework.stereotype.Service;

@Service
public class BuscarRestaurantePorIdUseCaseImpl implements BuscarRestaurantePorIdUseCase {
    private final RestauranteRepository restauranteRepository;

    public BuscarRestaurantePorIdUseCaseImpl(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    @Override
    public Restaurante buscaRestaurantePorId(Long id) {
        return this.restauranteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado com ID: " + id));
    }
}
