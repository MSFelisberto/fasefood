package br.com.fiap.fasefood.application.usecases.usuario.criar;

import br.com.fiap.fasefood.application.usecases.shared.endereco.EnderecoInput;
import br.com.fiap.fasefood.core.entities.TipoUsuario;

public record CriarUsuarioOutput(
        Long id,
        String nome,
        String email,
        String login,
        String senha,
        TipoUsuario tipoUsuarioNome,
        EnderecoInput endereco
) {
}
