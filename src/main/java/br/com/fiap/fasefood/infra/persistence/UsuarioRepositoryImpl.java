package br.com.fiap.fasefood.infra.persistence;

import br.com.fiap.fasefood.core.entities.Usuario;
import br.com.fiap.fasefood.core.gateways.UsuarioRepository;
import br.com.fiap.fasefood.infra.controllers.mapper.UserEntityMapper;
import br.com.fiap.fasefood.infra.persistence.entities.UsuarioEntity;
import br.com.fiap.fasefood.infra.persistence.jpa.UserJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository {

    private final UserJpaRepository userJpaRepository;

    public UsuarioRepositoryImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return userJpaRepository.findByIdAndAtivoTrue(id)
                .map(UserEntityMapper::toDomain);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return userJpaRepository.findByEmailAndAtivoTrue(email)
                .map(UserEntityMapper::toDomain);
    }

    @Override
    public Optional<Usuario> findByLogin(String login) {
        return userJpaRepository.findByLoginAndAtivoTrue(login)
                .map(UserEntityMapper::toDomain);
    }

    @Override
    public Usuario salvar(Usuario user) {
        UsuarioEntity entity = UserEntityMapper.toEntity(user);
        UsuarioEntity saved = userJpaRepository.save(entity);
        return UserEntityMapper.toDomain(saved);
    }

    @Override
    public Page<Usuario> listarTodosAtivos(Pageable paginacao) {
        Page<UsuarioEntity> pageEntity = userJpaRepository.findAllByAtivoTrue(paginacao);
        return pageEntity.map(entity -> UserEntityMapper.toDomain(entity));
    }
}