package br.com.fiap.fasefood.infra.persistence;

import br.com.fiap.fasefood.core.domain.entities.Cardapio;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioRepository;
import br.com.fiap.fasefood.infra.controller.mapper.CardapioMapper;
import br.com.fiap.fasefood.infra.persistence.entities.CardapioEntity;
import br.com.fiap.fasefood.infra.persistence.jpa.CardapioJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CardapioRepositoryImpl implements CardapioRepository {

    private final CardapioJpaRepository cardapioJpaRepository;

    public CardapioRepositoryImpl(CardapioJpaRepository cardapioJpaRepository) {
        this.cardapioJpaRepository = cardapioJpaRepository;
    }

    @Override
    public Optional<Cardapio> findById(Long id) {
        return cardapioJpaRepository.findById(id).map(CardapioMapper::toDomain);
    }

    @Override
    public List<Cardapio> findAll() {
        return cardapioJpaRepository.findAll().stream()
                .map(CardapioMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Cardapio> findByNome(String nome) {
        return Optional.empty();
    }

    @Override
    public Cardapio salvar(Cardapio cardapio) {
        var entity = CardapioMapper.toEntity(cardapio);
        var saved = cardapioJpaRepository.save(entity);
        return CardapioMapper.toDomain(saved);
    }

    @Override
    public Page<Cardapio> listarTodos(Pageable paginacao) {
        Page<CardapioEntity> pageEntity = cardapioJpaRepository.findAll(paginacao);
        return pageEntity.map(entity -> CardapioMapper.toDomain(entity));    }

    @Override
    public void deletar(Cardapio cardapio) {

    }
}
