package br.com.fiap.fasefood.infra.controller.mapper;

import br.com.fiap.fasefood.core.domain.entities.Endereco;
import br.com.fiap.fasefood.core.domain.entities.TipoUsuario;
import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.infra.controller.dto.CreateUserDTO;
import br.com.fiap.fasefood.infra.persistence.entities.UsuarioEntity;

public class UsuarioMapper {

    public static Usuario toDomain(CreateUserDTO dto, TipoUsuario tipoUsuario) {
        Endereco endereco = new Endereco(
                null,
                dto.endereco().logradouro(),
                dto.endereco().numero(),
                dto.endereco().cep(),
                dto.endereco().complemento(),
                dto.endereco().bairro(),
                dto.endereco().cidade(),
                dto.endereco().uf()
        );

        return new Usuario(
                null,
                dto.nome(),
                dto.email(),
                dto.login(),
                dto.senha(),
                null, // data de atualização será definida no domínio se necessário
                endereco,
                tipoUsuario,
                true
        );
    }

    public static Usuario toDomain(UsuarioEntity usuario) {
        if(usuario == null) {
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
}