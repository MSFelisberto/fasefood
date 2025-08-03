package br.com.fiap.fasefood.application.usecases.usuario.alterar;

import br.com.fiap.fasefood.application.usecases.shared.endereco.EnderecoInput;

public record UpdateUserDataInput(
        String nome,
        String email,
        EnderecoInput endereco
) {
}
