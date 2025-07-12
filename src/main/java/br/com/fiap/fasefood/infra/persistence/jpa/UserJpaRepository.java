package br.com.fiap.fasefood.infra.persistence.jpa;

import br.com.fiap.fasefood.infra.persistence.entities.UsuarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UsuarioEntity, Long> {

    Page<UsuarioEntity> findAllByAtivoTrue(Pageable pageable);

    Optional<UsuarioEntity> findByIdAndAtivoTrue(Long id);

    Optional<UsuarioEntity> findByLoginAndAtivoTrue(String login);

    Optional<UsuarioEntity> findByEmailAndAtivoTrue(String email);
}