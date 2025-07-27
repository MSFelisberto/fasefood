package br.com.fiap.fasefood.infra.persistence;

import br.com.fiap.fasefood.core.domain.entities.Restaurante;
import br.com.fiap.fasefood.core.usecase.gateways.RestauranteRepository;
import br.com.fiap.fasefood.infra.controller.mapper.RestauranteEntityMapper;
import br.com.fiap.fasefood.infra.persistence.jpa.RestauranteJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    public Page<Restaurante> listarTodosAtivos(Pageable paginacao) {
        return restauranteJpaRepository.findAllByAtivoTrue(paginacao)
                .map(RestauranteEntityMapper::toDomain);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        restauranteJpaRepository.softDeleteById(id);
    }
}