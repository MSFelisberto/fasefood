package br.com.fiap.fasefood.application.usecases.usuario.alterar;


import br.com.fiap.fasefood.application.usecases.usuario.listar.ListUserOutput;

public interface AtualizarUsuarioUseCase {
    ListUserOutput atualizar(Long id, UpdateUserDataInput updateUserDataInput);
}
