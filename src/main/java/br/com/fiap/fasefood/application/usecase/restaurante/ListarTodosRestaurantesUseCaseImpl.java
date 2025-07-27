package br.com.fiap.fasefood.application.usecase.restaurante;

import br.com.fiap.fasefood.core.domain.entities.restaurante.Restaurante;
import br.com.fiap.fasefood.core.usecase.gateways.RestauranteRepository;
import br.com.fiap.fasefood.core.usecase.interfaces.restaurante.ListarTodosRestaurantesUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarTodosRestaurantesUseCaseImpl implements ListarTodosRestaurantesUseCase {
    private final RestauranteRepository restauranteRepository;

    public ListarTodosRestaurantesUseCaseImpl(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    @Override
    public Page<Restaurante> buscaTodosRestaurantes(Pageable pageable) {
        return this.restauranteRepository.findAll(pageable);
    }
}
