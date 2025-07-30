package br.com.fiap.fasefood.application.usecase.restaurante.atualizar;

import br.com.fiap.fasefood.infra.controller.dto.restaurante.UpdateRestauranteDTO;
import br.com.fiap.fasefood.infra.controller.dto.restaurante.RestauranteResponseDTO;

public interface AtualizarRestauranteUseCase {
    RestauranteResponseDTO atualizar(Long id, UpdateRestauranteDTO dto);
}