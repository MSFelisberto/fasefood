package br.com.fiap.fasefood.application.usecase.usuario;

import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.UsuarioRepository;
import br.com.fiap.fasefood.core.usecase.interfaces.BuscarUsuarioPorIdUseCase;
import br.com.fiap.fasefood.infra.controller.dto.ListUserDTO;
import br.com.fiap.fasefood.infra.controller.mapper.UsuarioMapper;



public class BuscarUsuarioPorIdUseCaseImpl implements BuscarUsuarioPorIdUseCase {

    private final UsuarioRepository usuarioRepository;

    public BuscarUsuarioPorIdUseCaseImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public ListUserDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));

        return UsuarioMapper.toListUserDTO(usuario);
    }
}