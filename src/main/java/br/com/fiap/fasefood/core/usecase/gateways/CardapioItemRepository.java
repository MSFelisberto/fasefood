package br.com.fiap.fasefood.core.usecase.gateways;

import br.com.fiap.fasefood.core.domain.entities.CardapioItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CardapioItemRepository {
    CardapioItem salvar(CardapioItem item);
    Optional<CardapioItem> findById(Long id);
    Page<CardapioItem> findByCardapioId(Long cardapioId, Pageable pageable);
    void deletar(Long id);
}