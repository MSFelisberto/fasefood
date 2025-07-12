package br.com.fiap.fasefood.core.usecase.interfaces;

import br.com.fiap.fasefood.infra.controller.dto.ListUserDTO;

public interface BuscarUsuarioPorIdUseCase {
    ListUserDTO buscarPorId(Long id);
}