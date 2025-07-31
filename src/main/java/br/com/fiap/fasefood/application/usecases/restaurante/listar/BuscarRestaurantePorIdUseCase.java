package br.com.fiap.fasefood.application.usecases.restaurante.listar;

import br.com.fiap.fasefood.infra.controllers.dto.restaurante.RestauranteResponseDTO;

public interface BuscarRestaurantePorIdUseCase {
    RestauranteResponseDTO buscarPorId(Long id);
}