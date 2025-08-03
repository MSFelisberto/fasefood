package br.com.fiap.fasefood.infra.usecases.decorators.usuario;

import br.com.fiap.fasefood.application.usecases.usuario.alterar.AtualizarUsuarioUseCase;
import br.com.fiap.fasefood.application.usecases.usuario.alterar.UpdateUserDataInput;
import br.com.fiap.fasefood.application.usecases.usuario.listar.ListUserOutput;

public class TransactionalAtualizarUsuarioUseCase implements AtualizarUsuarioUseCase {

    private final AtualizarUsuarioUseCase decorator;

    public TransactionalAtualizarUsuarioUseCase(AtualizarUsuarioUseCase decorator) {
        this.decorator = decorator;
    }

    public ListUserOutput atualizar(Long id, UpdateUserDataInput updateUserDataInput) {
        return decorator.atualizar(id, updateUserDataInput);
    }
}
