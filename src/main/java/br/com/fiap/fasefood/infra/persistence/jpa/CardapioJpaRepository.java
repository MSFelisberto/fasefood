package br.com.fiap.fasefood.infra.persistence.jpa;

import br.com.fiap.fasefood.infra.persistence.entities.CardapioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardapioJpaRepository extends JpaRepository<CardapioEntity, Long> {
    Page<CardapioEntity> findAllByRestauranteIdAndAtivoTrue(Long restauranteId, Pageable pageable);
    Optional<CardapioEntity> findByIdAndAtivoTrue(Long id);
}