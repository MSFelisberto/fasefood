package br.com.fiap.fasefood.application.usecases.restaurante.criar;

import br.com.fiap.fasefood.application.usecases.restaurante.RestauranteOutput;

public interface CriarRestauranteUseCase {
    RestauranteOutput criar(CriarRestauranteInput criarRestauranteInput);
}