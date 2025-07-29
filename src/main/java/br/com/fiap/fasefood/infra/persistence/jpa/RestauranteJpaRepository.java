package br.com.fiap.fasefood.infra.persistence.jpa;

import br.com.fiap.fasefood.infra.persistence.entities.RestauranteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestauranteJpaRepository extends JpaRepository<RestauranteEntity, Long> {

    Page<RestauranteEntity> findAllByAtivoTrue(Pageable pageable);

    Optional<RestauranteEntity> findByIdAndAtivoTrue(Long id);
}