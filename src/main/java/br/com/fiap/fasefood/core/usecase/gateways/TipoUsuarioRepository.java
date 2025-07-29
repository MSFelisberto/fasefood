package br.com.fiap.fasefood.core.usecase.gateways;

import br.com.fiap.fasefood.core.domain.entities.TipoUsuario;

import java.util.List;
import java.util.Optional;

public interface TipoUsuarioRepository {

    Optional<TipoUsuario> findById(Long id);
    Optional<TipoUsuario> findByNome(String nome);
    List<TipoUsuario> findAll();
    TipoUsuario salvar(TipoUsuario tipoUsuario);
}
