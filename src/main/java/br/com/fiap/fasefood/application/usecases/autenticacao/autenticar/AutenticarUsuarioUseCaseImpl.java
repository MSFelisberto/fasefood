package br.com.fiap.fasefood.application.usecases.autenticacao.autenticar;

import br.com.fiap.fasefood.core.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.AuthenticationFailedException;
import br.com.fiap.fasefood.core.gateways.UsuarioRepository;
import br.com.fiap.fasefood.infra.controllers.dto.LoginRequestDTO;
import br.com.fiap.fasefood.infra.controllers.dto.LoginResponseDTO;
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
