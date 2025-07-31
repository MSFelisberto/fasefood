package br.com.fiap.fasefood.application.usecases.usuario.mappers;

import br.com.fiap.fasefood.application.usecases.usuario.listar.ListUserOutput;
import br.com.fiap.fasefood.core.entities.Usuario;

public class UsuarioOutputMapper {

    public static ListUserOutput toOutput(Usuario usuario) {
        if (usuario == null) return null;

        return new ListUserOutput(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getLogin(),
                usuario.getTipoUsuario(),
                usuario.getEndereco()
        );
    }
}
