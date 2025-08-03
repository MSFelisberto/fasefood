package br.com.fiap.fasefood.infra.usecases.decorators.usuario;

import br.com.fiap.fasefood.application.usecases.usuario.alterar.AlterarTipoUsuarioUseCase;
import br.com.fiap.fasefood.application.usecases.usuario.alterar.UpdateUserTypeInput;
import br.com.fiap.fasefood.application.usecases.usuario.listar.ListUserOutput;

public class TransactionalAlterarTipoUsuarioUseCase implements AlterarTipoUsuarioUseCase {

    private final AlterarTipoUsuarioUseCase decorator;

    public TransactionalAlterarTipoUsuarioUseCase(AlterarTipoUsuarioUseCase decorator) {
        this.decorator = decorator;
    }

    public ListUserOutput alterarTipoUsuario(Long usuarioId, UpdateUserTypeInput updateUserTypeInput) {
        return decorator.alterarTipoUsuario(usuarioId, updateUserTypeInput);
    }
}
