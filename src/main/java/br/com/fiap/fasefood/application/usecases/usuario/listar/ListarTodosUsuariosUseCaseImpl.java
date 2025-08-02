package br.com.fiap.fasefood.application.usecases.usuario.listar;

import br.com.fiap.fasefood.application.usecases.usuario.mappers.UsuarioOutputMapper;
import br.com.fiap.fasefood.core.entities.Usuario;
import br.com.fiap.fasefood.core.gateways.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ListarTodosUsuariosUseCaseImpl implements ListarTodosUsuariosUseCase {

    private final UsuarioRepository usuarioRepository;

    public ListarTodosUsuariosUseCaseImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Page<ListUserOutput> listar(Pageable pageable) {
        Page<Usuario> usuarios = usuarioRepository.listarTodosAtivos(pageable);
        return usuarios.map(UsuarioOutputMapper::toOutput);
    }
}