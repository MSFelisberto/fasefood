package br.com.fiap.fasefood.application.usecases.restaurante.atualizar;

import br.com.fiap.fasefood.infra.controllers.dto.restaurante.UpdateRestauranteDTO;
import br.com.fiap.fasefood.infra.controllers.dto.restaurante.RestauranteResponseDTO;

public interface AtualizarRestauranteUseCase {
    RestauranteResponseDTO atualizar(Long id, UpdateRestauranteDTO dto);
}