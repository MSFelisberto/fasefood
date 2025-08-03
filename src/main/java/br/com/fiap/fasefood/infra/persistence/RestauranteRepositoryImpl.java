package br.com.fiap.fasefood.infra.persistence;

import br.com.fiap.fasefood.application.usecases.shared.paginacao.PageOutput;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PaginationInput;
import br.com.fiap.fasefood.core.entities.Restaurante;
import br.com.fiap.fasefood.core.gateways.RestauranteRepository;
import br.com.fiap.fasefood.infra.controllers.mapper.restaurante.RestauranteEntityMapper;
import br.com.fiap.fasefood.infra.persistence.jpa.RestauranteJpaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepository {

    private final RestauranteJpaRepository restauranteJpaRepository;

    public RestauranteRepositoryImpl(RestauranteJpaRepository restauranteJpaRepository) {
        this.restauranteJpaRepository = restauranteJpaRepository;
    }

    @Override
    public Restaurante salvar(Restaurante restaurante) {
        var entity = RestauranteEntityMapper.toEntity(restaurante);
        var savedEntity = restauranteJpaRepository.save(entity);
        return RestauranteEntityMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Restaurante> findById(Long id) {
        return restauranteJpaRepository.findByIdAndAtivoTrue(id)
                .map(RestauranteEntityMapper::toDomain);
    }

    @Override
    public PageOutput<Restaurante> listarTodosAtivos(PaginationInput paginacao) {
        var pageable = PageRequest.of(
                paginacao.page(),
                paginacao.size(),
                Sort.by(Sort.Direction.fromString(paginacao.sortDirection()), paginacao.sortField())
        );

        var pageEntity = restauranteJpaRepository.findAllByAtivoTrue(pageable);

        var domainList = pageEntity.getContent().stream()
                .map(RestauranteEntityMapper::toDomain)
                .toList();

        return new PageOutput<>(
                domainList,
                pageEntity.getNumber(),
                pageEntity.getSize(),
                pageEntity.getTotalPages(),
                pageEntity.getTotalElements()
        );
    }
}