package br.com.fiap.fasefood.application.usecase.usuario;

import br.com.fiap.fasefood.core.domain.entities.TipoUsuario;
import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.TipoUsuarioRepository;
import br.com.fiap.fasefood.core.usecase.gateways.UsuarioRepository;
import br.com.fiap.fasefood.core.usecase.interfaces.AlterarTipoUsuarioUseCase;
import br.com.fiap.fasefood.infra.controller.dto.ListUserDTO;
import br.com.fiap.fasefood.infra.controller.dto.UpdateUserTypeDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AlterarTipoUsuarioUseCaseImpl implements AlterarTipoUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final TipoUsuarioRepository tipoUsuarioRepository;

    public AlterarTipoUsuarioUseCaseImpl(UsuarioRepository usuarioRepository, TipoUsuarioRepository tipoUsuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.tipoUsuarioRepository = tipoUsuarioRepository;
    }

    @Override
    @Transactional
    public ListUserDTO alterarTipoUsuario(Long usuarioId, UpdateUserTypeDTO dto) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + usuarioId));

        TipoUsuario novoTipoUsuario = tipoUsuarioRepository.findById(dto.tipoUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de usuário não encontrado com ID: " + dto.tipoUsuarioId()));

        usuario.alterarTipoUsuario(novoTipoUsuario);

        Usuario usuarioAtualizado = usuarioRepository.salvar(usuario);

        return new ListUserDTO(usuarioAtualizado);
    }
}
