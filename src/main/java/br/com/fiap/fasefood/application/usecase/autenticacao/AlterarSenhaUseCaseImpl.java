package br.com.fiap.fasefood.application.usecase.autenticacao;

import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.UsuarioRepository;
import br.com.fiap.fasefood.core.usecase.interfaces.AlterarSenhaUsuarioUseCase;
import br.com.fiap.fasefood.infra.controller.dto.ChangeUserPasswordDTO;

public class AlterarSenhaUseCaseImpl implements AlterarSenhaUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;

    public AlterarSenhaUseCaseImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void alterarSenha(Long id, ChangeUserPasswordDTO userData) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));

        usuario.alterarSenha(userData.senha());

        usuarioRepository.salvar(usuario);
    }
}
