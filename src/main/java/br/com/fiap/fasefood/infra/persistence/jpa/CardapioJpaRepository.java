package br.com.fiap.fasefood.infra.persistence.jpa;

import br.com.fiap.fasefood.infra.persistence.entities.CardapioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardapioJpaRepository extends JpaRepository<CardapioEntity, Long> {
}
