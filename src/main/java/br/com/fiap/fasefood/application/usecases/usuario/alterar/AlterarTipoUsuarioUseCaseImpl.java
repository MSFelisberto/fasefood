package br.com.fiap.fasefood.application.usecases.usuario.alterar;

import br.com.fiap.fasefood.application.usecases.usuario.listar.ListUserOutput;
import br.com.fiap.fasefood.core.entities.TipoUsuario;
import br.com.fiap.fasefood.core.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.gateways.TipoUsuarioRepository;
import br.com.fiap.fasefood.core.gateways.UsuarioRepository;
import br.com.fiap.fasefood.infra.controllers.dto.ListUserDTO;
import br.com.fiap.fasefood.infra.controllers.dto.UpdateUserTypeDTO;
import br.com.fiap.fasefood.infra.controllers.mapper.usuario.UsuarioMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

public class AlterarTipoUsuarioUseCaseImpl implements AlterarTipoUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final TipoUsuarioRepository tipoUsuarioRepository;

    public AlterarTipoUsuarioUseCaseImpl(UsuarioRepository usuarioRepository, TipoUsuarioRepository tipoUsuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.tipoUsuarioRepository = tipoUsuarioRepository;
    }

    @Override
    @Transactional
    public ListUserOutput alterarTipoUsuario(Long usuarioId, UpdateUserTypeDTO dto) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + usuarioId));

        TipoUsuario novoTipoUsuario = tipoUsuarioRepository.findById(dto.tipoUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de usuário não encontrado com ID: " + dto.tipoUsuarioId()));

        usuario.alterarTipoUsuario(novoTipoUsuario);

        Usuario usuarioAtualizado = usuarioRepository.salvar(usuario);

        return new ListUserOutput(
                usuarioAtualizado.getId(),
                usuarioAtualizado.getNome(),
                usuarioAtualizado.getEmail(),
                usuarioAtualizado.getLogin(),
                usuarioAtualizado.getTipoUsuario(),
                usuarioAtualizado.getEndereco()
        );
    }
}