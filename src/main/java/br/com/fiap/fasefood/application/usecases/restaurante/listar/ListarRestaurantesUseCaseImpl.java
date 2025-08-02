package br.com.fiap.fasefood.application.usecases.restaurante.listar;

import br.com.fiap.fasefood.application.usecases.restaurante.RestauranteOutput;
import br.com.fiap.fasefood.application.usecases.restaurante.mappers.RestauranteOutputMapper;
import br.com.fiap.fasefood.core.entities.Restaurante;
import br.com.fiap.fasefood.core.gateways.RestauranteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public class ListarRestaurantesUseCaseImpl implements ListarRestaurantesUseCase {

    private final RestauranteRepository restauranteRepository;

    public ListarRestaurantesUseCaseImpl(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    @Override
    public Page<RestauranteOutput> listar(Pageable pageable) {
        Page<Restaurante> restaurantePaginado =  restauranteRepository.listarTodosAtivos(pageable);
        return restaurantePaginado.map(RestauranteOutputMapper::toRestauranteOutput);

    }
}