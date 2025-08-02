package br.com.fiap.fasefood.application.usecases.usuario.listar;


import br.com.fiap.fasefood.application.usecases.shared.paginacao.PageOutput;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PaginationInput;

public interface ListarTodosUsuariosUseCase {
    PageOutput<ListUserOutput> listar(PaginationInput pageable);
}