package br.com.fiap.fasefood.core.usecase.interfaces.restaurante;

import br.com.fiap.fasefood.core.domain.entities.restaurante.Restaurante;
import br.com.fiap.fasefood.infra.controller.dto.restaurante.CriarRestauranteDTO;

public interface CriarRestauranteUsecase {
    Restaurante criarRestaurante(CriarRestauranteDTO data);
}
