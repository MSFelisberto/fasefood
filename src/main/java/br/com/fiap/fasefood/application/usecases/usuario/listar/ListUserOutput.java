package br.com.fiap.fasefood.application.usecases.usuario.listar;

import br.com.fiap.fasefood.core.entities.Endereco;
import br.com.fiap.fasefood.core.entities.TipoUsuario;

public record ListUserOutput(
        Long id,
        String nome,
        String email,
        String login,
        TipoUsuario tipoUsuario,
        Endereco endereco
) {
}
