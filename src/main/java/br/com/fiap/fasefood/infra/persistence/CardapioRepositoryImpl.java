package br.com.fiap.fasefood.infra.persistence;

import br.com.fiap.fasefood.application.usecases.shared.paginacao.PageOutput;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PaginationInput;
import br.com.fiap.fasefood.core.entities.Cardapio;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.gateways.CardapioRepository;
import br.com.fiap.fasefood.infra.controllers.mapper.cardapio.CardapioEntityMapper;
import br.com.fiap.fasefood.infra.persistence.jpa.CardapioJpaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
public class CardapioRepositoryImpl implements CardapioRepository {

    private final CardapioJpaRepository jpaRepository;

    public CardapioRepositoryImpl(CardapioJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Cardapio salvar(Cardapio cardapio) {
        var entity = CardapioEntityMapper.toEntity(cardapio);
        var savedEntity = jpaRepository.save(entity);
        return CardapioEntityMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Cardapio> findById(Long id) {
        return jpaRepository.findByIdAndAtivoTrue(id).map(CardapioEntityMapper::toDomain);
    }

    @Override
    public PageOutput<Cardapio> findByRestauranteId(Long restauranteId, PaginationInput pageable) {
        var springPageable = PageRequest.of(
                pageable.page(),
                pageable.size(),
                Sort.by(Sort.Direction.fromString(pageable.sortDirection()), pageable.sortField())
        );

        var pageEntity = jpaRepository.findAllByRestauranteIdAndAtivoTrue(restauranteId, springPageable);

        var domainList = pageEntity.getContent().stream()
                .map(CardapioEntityMapper::toDomain)
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
    public void deletar(Long id) {
        Cardapio cardapio = this.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cardápio com ID: " + id + " não encontrado para deleção."));

        cardapio.desativar();

        this.salvar(cardapio);
    }
}