package br.com.fiap.fasefood.infra.persistence;

import br.com.fiap.fasefood.core.domain.entities.TipoUsuario;
import br.com.fiap.fasefood.core.usecase.gateways.TipoUsuarioRepository;
import br.com.fiap.fasefood.infra.controller.mapper.TipoUsuarioMapper;
import br.com.fiap.fasefood.infra.persistence.entities.TipoUsuarioEntity;
import br.com.fiap.fasefood.infra.persistence.jpa.TipoUsuarioJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class TipoUsuarioRepositoryImpl implements TipoUsuarioRepository {

    private final TipoUsuarioJpaRepository tipoUsuarioJpaRepository;

    public TipoUsuarioRepositoryImpl(TipoUsuarioJpaRepository tipoUsuarioJpaRepository) {
        this.tipoUsuarioJpaRepository = tipoUsuarioJpaRepository;
    }

    @Override
    public Optional<TipoUsuario> findById(Long id) {
        return tipoUsuarioJpaRepository.findById(id).map(TipoUsuarioMapper::toDomain);
    }

    @Override
    public Optional<TipoUsuario> findByNome(String nome) {
        return tipoUsuarioJpaRepository.findByNome(nome).map(TipoUsuarioMapper::toDomain);
    }

    @Override
    public List<TipoUsuario> findAll() {
        return tipoUsuarioJpaRepository.findAll().stream()
                .map(TipoUsuarioMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public TipoUsuario salvar(TipoUsuario tipoUsuario) {
        var entity = TipoUsuarioMapper.toEntity(tipoUsuario);
        var saved = tipoUsuarioJpaRepository.save(entity);
        return TipoUsuarioMapper.toDomain(saved);
    }
}
