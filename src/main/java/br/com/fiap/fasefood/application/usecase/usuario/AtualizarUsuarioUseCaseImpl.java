package br.com.fiap.fasefood.application.usecase.usuario;

import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.UsuarioRepository;
import br.com.fiap.fasefood.core.usecase.interfaces.AtualizarUsuarioUseCase;
import br.com.fiap.fasefood.infra.controller.dto.ListUserDTO;
import br.com.fiap.fasefood.infra.controller.dto.UpdateUserDataDTO;
import org.springframework.stereotype.Service;

@Service
public class AtualizarUsuarioUseCaseImpl implements AtualizarUsuarioUseCase {
    private final UsuarioRepository usuarioRepository;

    public AtualizarUsuarioUseCaseImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public ListUserDTO atualizar(Long id, UpdateUserDataDTO dados) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));

        usuario.atualizarInformacoes(
                dados.nome(),
                dados.email(),
                usuario.getEndereco()
        );

        Usuario atualizado = usuarioRepository.salvar(usuario);

        return new ListUserDTO(atualizado);
    }
}
