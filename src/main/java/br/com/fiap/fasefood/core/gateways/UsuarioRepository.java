package br.com.fiap.fasefood.core.gateways;

import br.com.fiap.fasefood.core.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UsuarioRepository {
    Optional<Usuario> findById(Long id);
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByLogin(String login);
    Usuario salvar(Usuario usuario);
    Page<Usuario> listarTodosAtivos(Pageable paginacao);
}