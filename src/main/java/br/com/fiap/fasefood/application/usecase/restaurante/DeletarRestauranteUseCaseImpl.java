package br.com.fiap.fasefood.application.usecase.restaurante;

import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.RestauranteRepository;
import br.com.fiap.fasefood.core.usecase.interfaces.restaurante.DeletarRestauranteUseCase;
import org.springframework.stereotype.Service;

@Service
public class DeletarRestauranteUseCaseImpl implements DeletarRestauranteUseCase {

    private final RestauranteRepository restauranteRepository;

    public DeletarRestauranteUseCaseImpl(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    @Override
    public void deletar(Long id) {
        if (restauranteRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Restaurante não encontrado com ID: " + id);
        }
        restauranteRepository.deletar(id);
    }
}