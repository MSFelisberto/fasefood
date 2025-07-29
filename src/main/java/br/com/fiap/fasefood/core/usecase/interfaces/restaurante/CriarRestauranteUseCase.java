package br.com.fiap.fasefood.core.usecase.interfaces.restaurante;

import br.com.fiap.fasefood.infra.controller.dto.restaurante.CreateRestauranteDTO;
import br.com.fiap.fasefood.infra.controller.dto.restaurante.RestauranteResponseDTO;

public interface CriarRestauranteUseCase {
    RestauranteResponseDTO criar(CreateRestauranteDTO dto);
}