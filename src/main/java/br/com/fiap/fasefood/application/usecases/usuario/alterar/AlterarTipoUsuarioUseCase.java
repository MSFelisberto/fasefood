package br.com.fiap.fasefood.application.usecases.usuario.alterar;

import br.com.fiap.fasefood.application.usecases.usuario.listar.ListUserOutput;

public interface AlterarTipoUsuarioUseCase {
    ListUserOutput alterarTipoUsuario(Long usuarioId, UpdateUserTypeInput updateUserTypeInput);
}
