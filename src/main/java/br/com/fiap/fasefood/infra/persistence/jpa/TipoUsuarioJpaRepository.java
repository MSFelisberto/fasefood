package br.com.fiap.fasefood.infra.persistence.jpa;

import br.com.fiap.fasefood.infra.persistence.entities.TipoUsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoUsuarioJpaRepository extends JpaRepository<TipoUsuarioEntity, Long> {
    Optional<TipoUsuarioEntity> findByNome(String nome);
}
