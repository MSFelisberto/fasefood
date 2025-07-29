package br.com.fiap.fasefood.application.usecase.restaurante;

import br.com.fiap.fasefood.core.domain.entities.Restaurante;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.RestauranteRepository;
import br.com.fiap.fasefood.core.usecase.interfaces.restaurante.BuscarRestaurantePorIdUseCase;
import br.com.fiap.fasefood.infra.controller.dto.restaurante.RestauranteResponseDTO;
import br.com.fiap.fasefood.infra.controller.mapper.restaurante.RestauranteMapper;
import org.springframework.stereotype.Service;

@Service
public class BuscarRestaurantePorIdUseCaseImpl implements BuscarRestaurantePorIdUseCase {

    private final RestauranteRepository restauranteRepository;

    public BuscarRestaurantePorIdUseCaseImpl(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    @Override
    public RestauranteResponseDTO buscarPorId(Long id) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado com ID: " + id));
        return RestauranteMapper.toResponseDTO(restaurante);
    }
}