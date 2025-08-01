package br.com.fiap.fasefood.core.usecase.interfaces;


import br.com.fiap.fasefood.infra.controller.dto.ListUserDTO;
import br.com.fiap.fasefood.infra.controller.dto.UpdateUserDataDTO;

public interface AtualizarUsuarioUseCase {
    ListUserDTO atualizar(Long id, UpdateUserDataDTO updateUserDTO);
}
