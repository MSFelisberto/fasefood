package br.com.fiap.fasefood.infra.controllers.mapper.usuario;

import br.com.fiap.fasefood.application.usecases.shared.endereco.EnderecoInput;
import br.com.fiap.fasefood.application.usecases.usuario.alterar.UpdateUserDataInput;
import br.com.fiap.fasefood.application.usecases.usuario.criar.CriarUsuarioInput;
import br.com.fiap.fasefood.application.usecases.usuario.criar.CriarUsuarioOutput;
import br.com.fiap.fasefood.application.usecases.usuario.listar.ListUserOutput;
import br.com.fiap.fasefood.core.entities.Endereco;
import br.com.fiap.fasefood.core.entities.TipoUsuario;
import br.com.fiap.fasefood.core.entities.Usuario;
import br.com.fiap.fasefood.infra.controllers.dto.CreateUserDTO;
import br.com.fiap.fasefood.infra.controllers.dto.ListUserDTO;
import br.com.fiap.fasefood.infra.controllers.dto.UpdateUserDataDTO;
import br.com.fiap.fasefood.infra.controllers.mapper.endereco.EnderecoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public class UsuarioMapper {
    public static ListUserDTO toListUserDTO(CriarUsuarioOutput usuario) {
        if (usuario == null) {
            return null;
        }
        return new ListUserDTO(
                usuario.id(),
                usuario.nome(),
                usuario.email(),
                usuario.login(),
                usuario.tipoUsuarioNome(),
                usuario.endereco()
        );
    }

    public static ListUserDTO toListUserDTO(ListUserOutput usuario) {
        if (usuario == null) {
            return null;
        }
        return new ListUserDTO(
                usuario.id(),
                usuario.nome(),
                usuario.email(),
                usuario.login(),
                usuario.tipoUsuario(),
                new EnderecoInput(
                        usuario.endereco().getLogradouro(),
                        usuario.endereco().getNumero(),
                        usuario.endereco().getCep(),
                        usuario.endereco().getComplemento(),
                        usuario.endereco().getBairro(),
                        usuario.endereco().getCidade(),
                        usuario.endereco().getUf()
                )
        );
    }

    public static Page<ListUserDTO> toListUserOutputPaginacao(Page<ListUserOutput> listUserPage) {
        List<ListUserDTO> dtos = listUserPage
                .stream()
                .map(UsuarioMapper::toListUserDTO)
                .toList();

        return new PageImpl<>(
                dtos,
                listUserPage.getPageable(),
                listUserPage.getTotalElements()
        );
    }

    public static CriarUsuarioInput toCriarUsuarioInput(CreateUserDTO dto) {
        return new CriarUsuarioInput(
                dto.nome(),
                dto.email(),
                dto.login(),
                dto.senha(),
                dto.tipoUsuarioNome(),
                EnderecoMapper.toEnderecoInput(dto.endereco())
        );
    }

    public static UpdateUserDataInput toUpdateUserDataInput(UpdateUserDataDTO dto) {
        return new UpdateUserDataInput(
                dto.nome(),
                dto.email(),
                EnderecoMapper.toEnderecoInput(dto.endereco())
        );
    }

}