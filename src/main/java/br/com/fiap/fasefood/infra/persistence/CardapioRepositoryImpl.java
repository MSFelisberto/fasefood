package br.com.fiap.fasefood.infra.persistence;

import br.com.fiap.fasefood.core.domain.entities.Cardapio;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioRepository;
import br.com.fiap.fasefood.infra.controller.mapper.cardapio.CardapioEntityMapper;
import br.com.fiap.fasefood.infra.persistence.jpa.CardapioJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<Cardapio> findByRestauranteId(Long restauranteId, Pageable pageable) {
        return jpaRepository.findAllByRestauranteIdAndAtivoTrue(restauranteId, pageable).map(CardapioEntityMapper::toDomain);
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