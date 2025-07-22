package br.com.fiap.fasefood.core.usecase.interfaces.restaurante;

import br.com.fiap.fasefood.core.domain.entities.restaurante.Restaurante;

public interface DeletarRestaurantePorIdUseCase {
    void deletarPorId(Long id);
}
