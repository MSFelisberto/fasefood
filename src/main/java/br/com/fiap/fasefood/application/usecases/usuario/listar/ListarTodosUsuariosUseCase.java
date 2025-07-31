package br.com.fiap.fasefood.application.usecases.usuario.listar;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListarTodosUsuariosUseCase {
    Page<ListUserOutput> listar(Pageable pageable);
}