package br.com.fiap.fasefood.infra.persistence.jpa;

import br.com.fiap.fasefood.infra.persistence.entities.CardapioItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardapioItemJpaRepository extends JpaRepository<CardapioItemEntity, Long> {

    Page<CardapioItemEntity> findAllByCardapioIdAndAtivoTrue(Long cardapioId, Pageable pageable);
    Optional<CardapioItemEntity> findByIdAndAtivoTrue(Long id);
}