package br.com.fiap.fasefood.application.usecases.restaurante.listar;

import br.com.fiap.fasefood.application.usecases.restaurante.RestauranteOutput;
import br.com.fiap.fasefood.application.usecases.restaurante.mappers.RestauranteOutputMapper;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PageOutput;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PaginationInput;
import br.com.fiap.fasefood.core.entities.Restaurante;
import br.com.fiap.fasefood.core.gateways.RestauranteRepository;


public class ListarRestaurantesUseCaseImpl implements ListarRestaurantesUseCase {

    private final RestauranteRepository restauranteRepository;

    public ListarRestaurantesUseCaseImpl(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    @Override
    public PageOutput<RestauranteOutput> listar(PaginationInput pageable) {
        var restaurantePage = restauranteRepository.listarTodosAtivos(pageable);

        var restauranteOutputContent = restaurantePage.content().stream()
                .map(RestauranteOutputMapper::toRestauranteOutput)
                .toList();

        return new PageOutput<>(
                restauranteOutputContent,
                restaurantePage.currentPage(),
                restaurantePage.size(),
                restaurantePage.totalPages(),
                restaurantePage.totalElements()
        );
    }
}