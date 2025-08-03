package br.com.fiap.fasefood.application.usecases.restaurante.listar;

import br.com.fiap.fasefood.application.usecases.restaurante.RestauranteOutput;

public interface BuscarRestaurantePorIdUseCase {
    RestauranteOutput buscarPorId(Long id);
}