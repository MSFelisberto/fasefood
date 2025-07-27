package br.com.fiap.fasefood.infra.persistence.jpa;

import br.com.fiap.fasefood.infra.persistence.entities.RestauranteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RestauranteJpaRepository extends JpaRepository<RestauranteEntity, Long> {

    Page<RestauranteEntity> findAllByAtivoTrue(Pageable pageable);

    Optional<RestauranteEntity> findByIdAndAtivoTrue(Long id);

    @Modifying
    @Query("UPDATE RestauranteEntity r SET r.ativo = false WHERE r.id = :id")
    void softDeleteById(@Param("id") Long id);
}