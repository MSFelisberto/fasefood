package br.com.fiap.fasefood.application.usecases.usuario.alterar;

import br.com.fiap.fasefood.application.usecases.usuario.listar.ListUserOutput;
import br.com.fiap.fasefood.infra.controllers.dto.ListUserDTO;
import br.com.fiap.fasefood.infra.controllers.dto.UpdateUserTypeDTO;

public interface AlterarTipoUsuarioUseCase {
    ListUserOutput alterarTipoUsuario(Long usuarioId, UpdateUserTypeInput updateUserTypeInput);
}
