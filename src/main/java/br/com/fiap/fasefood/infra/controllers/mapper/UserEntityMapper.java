package br.com.fiap.fasefood.infra.controllers.mapper;

import br.com.fiap.fasefood.core.entities.Endereco;
import br.com.fiap.fasefood.core.entities.Usuario;
import br.com.fiap.fasefood.infra.persistence.entities.UsuarioEntity;

public class UserEntityMapper {

    public static Usuario toDomain(UsuarioEntity usuario) {
        if (usuario == null) {
            return null;
        }

        return Usuario.criarUsuario(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getLogin(),
                usuario.getSenha(),
                usuario.getDataUltimaAtualizacao(),
                Endereco.criarEndereco(
                        usuario.getEnderecoEntity().getId(),
                        usuario.getEnderecoEntity().getLogradouro(),
                        usuario.getEnderecoEntity().getNumero(),
                        usuario.getEnderecoEntity().getCep(),
                        usuario.getEnderecoEntity().getComplemento(),
                        usuario.getEnderecoEntity().getBairro(),
                        usuario.getEnderecoEntity().getCidade(),
                        usuario.getEnderecoEntity().getUf()

                ),
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