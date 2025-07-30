package br.com.fiap.fasefood.application.usecase.usuario.alterar;


import br.com.fiap.fasefood.infra.controller.dto.ListUserDTO;
import br.com.fiap.fasefood.infra.controller.dto.UpdateUserDataDTO;

public interface AtualizarUsuarioUseCase {
    ListUserDTO atualizar(Long id, UpdateUserDataDTO updateUserDTO);
}
