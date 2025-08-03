package br.com.fiap.fasefood.infra.persistence;

import br.com.fiap.fasefood.application.usecases.shared.paginacao.PageOutput;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PaginationInput;
import br.com.fiap.fasefood.core.entities.CardapioItem;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.gateways.CardapioItemRepository;
import br.com.fiap.fasefood.infra.controllers.mapper.cardapio.CardapioItemEntityMapper;
import br.com.fiap.fasefood.infra.persistence.jpa.CardapioItemJpaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public PageOutput<CardapioItem> findByCardapioId(Long cardapioId, PaginationInput pageable) {
        var springPageable = PageRequest.of(
                pageable.page(),
                pageable.size(),
                Sort.by(Sort.Direction.fromString(pageable.sortDirection()), pageable.sortField())
        );

        var pageEntity = jpaRepository.findAllByCardapioIdAndAtivoTrue(cardapioId, springPageable);

        var domainList = pageEntity.getContent().stream()
                .map(CardapioItemEntityMapper::toDomain)
                .toList();

        return new PageOutput<>(
                domainList,
                pageEntity.getNumber(),
                pageEntity.getSize(),
                pageEntity.getTotalPages(),
                pageEntity.getTotalElements()
        );
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