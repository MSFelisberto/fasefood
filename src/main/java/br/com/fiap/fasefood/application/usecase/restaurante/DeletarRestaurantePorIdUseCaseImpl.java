package br.com.fiap.fasefood.application.usecase.restaurante;

import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.RestauranteRepository;
import br.com.fiap.fasefood.core.usecase.interfaces.restaurante.DeletarRestaurantePorIdUseCase;
import org.springframework.stereotype.Service;

@Service
public class DeletarRestaurantePorIdUseCaseImpl implements DeletarRestaurantePorIdUseCase {
    private final RestauranteRepository restauranteRepository;

    public DeletarRestaurantePorIdUseCaseImpl(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    @Override
    public void deletarPorId(Long id) {
        this.restauranteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado com ID: " + id));

        this.restauranteRepository.deleteById(id);
    }
}
