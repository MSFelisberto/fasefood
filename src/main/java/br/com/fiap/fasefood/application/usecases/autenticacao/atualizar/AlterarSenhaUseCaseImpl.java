package br.com.fiap.fasefood.application.usecases.autenticacao.atualizar;

import br.com.fiap.fasefood.core.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.gateways.UsuarioRepository;
import br.com.fiap.fasefood.infra.controllers.dto.ChangeUserPasswordDTO;
import org.springframework.stereotype.Service;

@Service
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
