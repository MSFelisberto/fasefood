package br.com.fiap.fasefood.infra.controllers.mapper.usuario;

import br.com.fiap.fasefood.application.usecases.shared.endereco.EnderecoInput;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PageOutput;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PaginationInput;
import br.com.fiap.fasefood.application.usecases.usuario.alterar.UpdateUserDataInput;
import br.com.fiap.fasefood.application.usecases.usuario.alterar.UpdateUserTypeInput;
import br.com.fiap.fasefood.application.usecases.usuario.criar.CriarUsuarioInput;
import br.com.fiap.fasefood.application.usecases.usuario.criar.CriarUsuarioOutput;
import br.com.fiap.fasefood.application.usecases.usuario.listar.ListUserOutput;
import br.com.fiap.fasefood.infra.controllers.dto.usuario.CreateUserDTO;
import br.com.fiap.fasefood.infra.controllers.dto.usuario.ListUserDTO;
import br.com.fiap.fasefood.infra.controllers.dto.usuario.UpdateUserDataDTO;
import br.com.fiap.fasefood.infra.controllers.dto.usuario.UpdateUserTypeDTO;
import br.com.fiap.fasefood.infra.controllers.mapper.endereco.EnderecoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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

    public static Page<ListUserDTO> toListUserOutputPaginacao(PageOutput<ListUserOutput> listUserPage) {
        List<ListUserDTO> dtos = listUserPage.content()
                .stream()
                .map(UsuarioMapper::toListUserDTO)
                .toList();

        return new PageImpl<>(
                dtos,
                PageRequest.of(listUserPage.currentPage(), listUserPage.size()),
                listUserPage.totalElements()
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

    public static UpdateUserTypeInput toUpdateUserTypeInput(UpdateUserTypeDTO dto) {
        return new UpdateUserTypeInput(dto.tipoUsuarioId());
    }

    public static PaginationInput toPaginationInput(Pageable pageable) {
        if (pageable == null) {
            return new PaginationInput(0, 10, "id", "ASC");
        }

        String sortField = "id"; // Default
        String sortDirection = "ASC"; // Default

        if (pageable.getSort().isSorted()) {
            var sortIterator = pageable.getSort().iterator();
            if (sortIterator.hasNext()) {
                var order = sortIterator.next();
                sortField = order.getProperty();
                sortDirection = order.getDirection().name();
            }
        }

        return new PaginationInput(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                sortField,
                sortDirection
        );
    }

}