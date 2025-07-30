package br.com.fiap.fasefood.application.usecase.restaurante.criar;

import br.com.fiap.fasefood.infra.controller.dto.restaurante.CreateRestauranteDTO;
import br.com.fiap.fasefood.infra.controller.dto.restaurante.RestauranteResponseDTO;

public interface CriarRestauranteUseCase {
    RestauranteResponseDTO criar(CreateRestauranteDTO dto);
}