package br.com.fiap.fasefood.core.usecase.interfaces.cardapio;

import br.com.fiap.fasefood.infra.controller.dto.ListCardapioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListarTodosCardapioUseCase {
    Page<ListCardapioDTO> listar(Pageable pageable);
}