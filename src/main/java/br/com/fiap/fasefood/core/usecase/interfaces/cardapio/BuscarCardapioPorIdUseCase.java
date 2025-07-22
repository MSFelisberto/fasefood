package br.com.fiap.fasefood.core.usecase.interfaces.cardapio;

import br.com.fiap.fasefood.infra.controller.dto.ListCardapioDTO;

public interface BuscarCardapioPorIdUseCase {
    ListCardapioDTO buscarPorId(Long id);
}