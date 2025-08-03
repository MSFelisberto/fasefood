package br.com.fiap.fasefood.core.gateways;

import br.com.fiap.fasefood.application.usecases.shared.paginacao.PageOutput;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PaginationInput;
import br.com.fiap.fasefood.core.entities.CardapioItem;

import java.util.Optional;

public interface CardapioItemRepository {
    CardapioItem salvar(CardapioItem item);
    Optional<CardapioItem> findById(Long id);
    PageOutput<CardapioItem> findByCardapioId(Long cardapioId, PaginationInput pageable);
    void remover(Long id);
}