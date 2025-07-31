package br.com.fiap.fasefood.core.gateways;

import br.com.fiap.fasefood.core.entities.TipoUsuario;

import java.util.List;
import java.util.Optional;

public interface TipoUsuarioRepository {

    Optional<TipoUsuario> findById(Long id);
    Optional<TipoUsuario> findByNome(String nome);
    List<TipoUsuario> findAll();
    TipoUsuario salvar(TipoUsuario tipoUsuario);
}
