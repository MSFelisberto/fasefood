package br.com.fiap.fasefood.application.usecases.restaurante.atualizar;

import br.com.fiap.fasefood.application.usecases.restaurante.RestauranteOutput;

public interface AtualizarRestauranteUseCase {
    RestauranteOutput atualizar(Long id, UpdateRestauranteInput input);
}