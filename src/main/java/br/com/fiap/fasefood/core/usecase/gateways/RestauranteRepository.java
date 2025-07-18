package br.com.fiap.fasefood.core.usecase.gateways;

import br.com.fiap.fasefood.core.domain.entities.restaurante.Restaurante;

public interface RestauranteRepository {
    Restaurante create(Restaurante restaurante);
}
