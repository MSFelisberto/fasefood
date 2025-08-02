package br.com.fiap.fasefood.application.usecases.usuario.listar;

import br.com.fiap.fasefood.application.usecases.shared.paginacao.PageOutput;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PaginationInput;
import br.com.fiap.fasefood.application.usecases.usuario.mappers.UsuarioOutputMapper;
import br.com.fiap.fasefood.core.gateways.UsuarioRepository;
public class ListarTodosUsuariosUseCaseImpl implements ListarTodosUsuariosUseCase {

    private final UsuarioRepository usuarioRepository;

    public ListarTodosUsuariosUseCaseImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public PageOutput<ListUserOutput> listar(PaginationInput paginacao) {
        var usuarioDomainPage = usuarioRepository.listarTodosAtivos(paginacao);

        var listUserOutputContent = usuarioDomainPage.content().stream()
                .map(UsuarioOutputMapper::toOutput)
                .toList();

        return new PageOutput<>(
                listUserOutputContent,
                usuarioDomainPage.currentPage(),
                usuarioDomainPage.size(),
                usuarioDomainPage.totalPages(),
                usuarioDomainPage.totalElements()
        );
    }
}