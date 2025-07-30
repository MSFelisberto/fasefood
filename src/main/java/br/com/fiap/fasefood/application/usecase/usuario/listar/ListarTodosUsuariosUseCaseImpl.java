package br.com.fiap.fasefood.application.usecase.usuario.listar;

import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.core.usecase.gateways.UsuarioRepository;
import br.com.fiap.fasefood.infra.controller.dto.ListUserDTO;
import br.com.fiap.fasefood.infra.controller.mapper.UsuarioMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListarTodosUsuariosUseCaseImpl implements ListarTodosUsuariosUseCase {

    private final UsuarioRepository usuarioRepository;

    public ListarTodosUsuariosUseCaseImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Page<ListUserDTO> listar(Pageable pageable) {
        Page<Usuario> usuarios = usuarioRepository.listarTodosAtivos(pageable);
        return usuarios.map(UsuarioMapper::toListUserDTO);
    }
}