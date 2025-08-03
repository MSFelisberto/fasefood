package br.com.fiap.fasefood.application.usecases.cardapio.listar;

import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioItemOutput;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PageOutput;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PaginationInput;

public interface ListarCardapioItensUseCase {
    PageOutput<CriarCardapioItemOutput> listar(Long cardapioId, PaginationInput pageable);
}