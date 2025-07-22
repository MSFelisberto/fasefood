package br.com.fiap.fasefood.core.usecase.interfaces.usuario;

import br.com.fiap.fasefood.infra.controller.dto.ListUserDTO;
import br.com.fiap.fasefood.infra.controller.dto.UpdateUserTypeDTO;

public interface AlterarTipoUsuarioUseCase {
    ListUserDTO alterarTipoUsuario(Long usuarioId, UpdateUserTypeDTO updateUserTypeDTO);
}
