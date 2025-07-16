package br.com.fiap.fasefood.infra.controller.mapper;

import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.infra.persistence.entities.UsuarioEntity;

public class UserEntityMapper {

    public static Usuario toDomain(UsuarioEntity usuario) {
        if (usuario == null) {
            return null;
        }
        return new Usuario(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getLogin(),
                usuario.getSenha(),
                usuario.getDataUltimaAtualizacao(),
                EnderecoEntityMapper.toDomain(usuario.getEnderecoEntity()),
                TipoUsuarioMapper.toDomain(usuario.getTipoUsuario()),
                usuario.isAtivo()
        );
    }

    public static UsuarioEntity toEntity(Usuario user) {
        if (user == null) {
            return null;
        }
        return new UsuarioEntity(
                user.getId(),
                user.getNome(),
                user.getEmail(),
                user.getLogin(),
                user.getSenha(),
                user.getDataUltimaAtualizacao(),
                TipoUsuarioMapper.toEntity(user.getTipoUsuario()),
                EnderecoEntityMapper.toEntity(user.getEndereco()),
                user.isAtivo()
        );
    }
}