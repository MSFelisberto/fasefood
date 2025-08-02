package br.com.fiap.fasefood.infra.persistence;

import br.com.fiap.fasefood.core.entities.CardapioItem;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.gateways.CardapioItemRepository;
import br.com.fiap.fasefood.infra.controllers.mapper.cardapio.CardapioItemEntityMapper;
import br.com.fiap.fasefood.infra.persistence.jpa.CardapioItemJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class CardapioItemRepositoryImpl implements CardapioItemRepository {

    private final CardapioItemJpaRepository jpaRepository;

    public CardapioItemRepositoryImpl(CardapioItemJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public CardapioItem salvar(CardapioItem item) {
        var entity = CardapioItemEntityMapper.toEntity(item);
        var savedEntity = jpaRepository.save(entity);
        return CardapioItemEntityMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<CardapioItem> findById(Long id) {
        return jpaRepository.findByIdAndAtivoTrue(id)
                .map(CardapioItemEntityMapper::toDomain);
    }

    @Override
    public Page<CardapioItem> findByCardapioId(Long cardapioId, Pageable pageable) {
        return jpaRepository.findAllByCardapioIdAndAtivoTrue(cardapioId, pageable)
                .map(CardapioItemEntityMapper::toDomain);
    }

    @Override
    @Transactional
    public void remover(Long id) {
        CardapioItem item = this.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item de cardápio com ID: " + id + " não encontrado para deleção."));

        item.desativar();

        this.salvar(item);
    }
}