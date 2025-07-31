package br.com.fiap.fasefood.application.usecases.usuario.criar;

import br.com.fiap.fasefood.application.usecases.shared.endereco.EnderecoInput;

public record CriarUsuarioInput(
        String nome,
        String email,
        String login,
        String senha,
        String tipoUsuarioNome,
        EnderecoInput endereco
) {
}
