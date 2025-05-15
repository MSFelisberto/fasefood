package br.com.fiap.fasefood.repositories;

import br.com.fiap.fasefood.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Page<UserEntity> findAllByAtivoTrue(Pageable pageable);

    Optional<UserEntity> findByIdAndAtivoTrue(Long id);

    Optional<UserEntity> findByLoginAndAtivoTrue(String login);
}
