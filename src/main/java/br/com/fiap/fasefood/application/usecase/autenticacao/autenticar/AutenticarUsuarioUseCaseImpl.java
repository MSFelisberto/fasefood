package br.com.fiap.fasefood.application.usecase.autenticacao.autenticar;

import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.AuthenticationFailedException;
import br.com.fiap.fasefood.core.usecase.gateways.UsuarioRepository;
import br.com.fiap.fasefood.infra.controller.dto.LoginRequestDTO;
import br.com.fiap.fasefood.infra.controller.dto.LoginResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class AutenticarUsuarioUseCaseImpl implements AutenticarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;

    public AutenticarUsuarioUseCaseImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public LoginResponseDTO autenticar(LoginRequestDTO loginRequest) {
        Usuario usuario = usuarioRepository.findByLogin(loginRequest.login())
                .orElseThrow(() -> new AuthenticationFailedException("Login ou senha incorretos"));

        if (!usuario.getSenha().equals(loginRequest.senha())) {
            throw new AuthenticationFailedException("Login ou senha incorretos");
        }

        return new LoginResponseDTO(true, "Login realizado com sucesso");
    }
}
