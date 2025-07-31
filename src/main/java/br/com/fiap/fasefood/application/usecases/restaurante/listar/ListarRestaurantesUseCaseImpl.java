package br.com.fiap.fasefood.application.usecases.restaurante.listar;

import br.com.fiap.fasefood.core.gateways.RestauranteRepository;
import br.com.fiap.fasefood.infra.controllers.dto.restaurante.RestauranteResponseDTO;
import br.com.fiap.fasefood.infra.controllers.mapper.restaurante.RestauranteMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListarRestaurantesUseCaseImpl implements ListarRestaurantesUseCase {

    private final RestauranteRepository restauranteRepository;

    public ListarRestaurantesUseCaseImpl(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    @Override
    public Page<RestauranteResponseDTO> listar(Pageable pageable) {
        return restauranteRepository.listarTodosAtivos(pageable)
                .map(RestauranteMapper::toResponseDTO);
    }
}