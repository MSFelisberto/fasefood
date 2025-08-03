package br.com.fiap.fasefood.infra.persistence;

import br.com.fiap.fasefood.application.usecases.shared.paginacao.PageOutput;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PaginationInput;
import br.com.fiap.fasefood.core.entities.Usuario;
import br.com.fiap.fasefood.core.gateways.UsuarioRepository;
import br.com.fiap.fasefood.infra.controllers.mapper.UserEntityMapper;
import br.com.fiap.fasefood.infra.persistence.entities.UsuarioEntity;
import br.com.fiap.fasefood.infra.persistence.jpa.UserJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    public PageOutput<Usuario> listarTodosAtivos(PaginationInput paginacao) {
        var pageable = PageRequest.of(
                paginacao.page(),
                paginacao.size(),
                Sort.by(Sort.Direction.fromString(paginacao.sortDirection()), paginacao.sortField())
        );

        Page<UsuarioEntity> pageEntity = userJpaRepository.findAllByAtivoTrue(pageable);

        List<Usuario> usuariosDomain = pageEntity.getContent().stream()
                .map(UserEntityMapper::toDomain)
                .toList();

        return new PageOutput<>(
                usuariosDomain,
                pageEntity.getNumber(),
                pageEntity.getSize(),
                pageEntity.getTotalPages(),
                pageEntity.getTotalElements()
        );
    }
}