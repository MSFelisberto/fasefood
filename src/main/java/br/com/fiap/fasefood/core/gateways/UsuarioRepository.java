package br.com.fiap.fasefood.core.gateways;

import br.com.fiap.fasefood.application.usecases.shared.paginacao.PageOutput;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PaginationInput;
import br.com.fiap.fasefood.core.entities.Usuario;

import java.util.Optional;

public interface UsuarioRepository {
    Optional<Usuario> findById(Long id);
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByLogin(String login);
    Usuario salvar(Usuario usuario);
    PageOutput<Usuario> listarTodosAtivos(PaginationInput paginacao);
}