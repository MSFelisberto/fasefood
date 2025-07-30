package br.com.fiap.fasefood.application.usecase.usuario.listar;

import br.com.fiap.fasefood.infra.controller.dto.ListUserDTO;

public interface BuscarUsuarioPorIdUseCase {
    ListUserDTO buscarPorId(Long id);
}