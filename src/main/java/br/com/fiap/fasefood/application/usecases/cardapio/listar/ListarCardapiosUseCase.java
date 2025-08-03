package br.com.fiap.fasefood.application.usecases.cardapio.listar;

import br.com.fiap.fasefood.application.usecases.shared.paginacao.PageOutput;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PaginationInput;

public interface ListarCardapiosUseCase {
    PageOutput<CardapioResponseOutput> listar(Long restauranteId, PaginationInput pageable);
}