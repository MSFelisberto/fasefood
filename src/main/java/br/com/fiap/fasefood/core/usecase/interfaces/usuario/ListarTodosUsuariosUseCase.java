package br.com.fiap.fasefood.core.usecase.interfaces.usuario;

import br.com.fiap.fasefood.infra.controller.dto.ListUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListarTodosUsuariosUseCase {
    Page<ListUserDTO> listar(Pageable pageable);
}