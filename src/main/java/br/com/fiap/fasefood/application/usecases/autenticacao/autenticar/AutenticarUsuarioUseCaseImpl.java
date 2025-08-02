package br.com.fiap.fasefood.application.usecases.autenticacao.autenticar;

import br.com.fiap.fasefood.core.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.AuthenticationFailedException;
import br.com.fiap.fasefood.core.gateways.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class AutenticarUsuarioUseCaseImpl implements AutenticarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;

    public AutenticarUsuarioUseCaseImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public LoginResponseOutput autenticar(LoginRequestInput loginInput) {
        Usuario usuario = usuarioRepository.findByLogin(loginInput.login())
                .orElseThrow(() -> new AuthenticationFailedException("Login ou senha incorretos"));

        if (!usuario.getSenha().equals(loginInput.senha())) {
            throw new AuthenticationFailedException("Login ou senha incorretos");
        }

        return new LoginResponseOutput(true, "Login realizado com sucesso");
    }
}
