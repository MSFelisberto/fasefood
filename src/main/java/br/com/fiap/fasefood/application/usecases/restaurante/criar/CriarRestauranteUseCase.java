package br.com.fiap.fasefood.application.usecases.restaurante.criar;

import br.com.fiap.fasefood.infra.controllers.dto.restaurante.CreateRestauranteDTO;
import br.com.fiap.fasefood.infra.controllers.dto.restaurante.RestauranteResponseDTO;

public interface CriarRestauranteUseCase {
    RestauranteResponseDTO criar(CreateRestauranteDTO dto);
}