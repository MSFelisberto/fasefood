package br.com.fiap.fasefood.application.usecase.restaurante.listar;

import br.com.fiap.fasefood.infra.controller.dto.restaurante.RestauranteResponseDTO;

public interface BuscarRestaurantePorIdUseCase {
    RestauranteResponseDTO buscarPorId(Long id);
}