package br.com.fiap.fasefood.application.usecases.usuario.alterar;


import br.com.fiap.fasefood.application.usecases.usuario.listar.ListUserOutput;
import br.com.fiap.fasefood.infra.controllers.dto.ListUserDTO;
import br.com.fiap.fasefood.infra.controllers.dto.UpdateUserDataDTO;

public interface AtualizarUsuarioUseCase {
    ListUserOutput atualizar(Long id, UpdateUserDataInput updateUserDataInput);
}
