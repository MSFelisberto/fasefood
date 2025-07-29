package br.com.fiap.fasefood.config;

import br.com.fiap.fasefood.application.usecase.autenticacao.AlterarSenhaUseCaseImpl;
import br.com.fiap.fasefood.application.usecase.autenticacao.AutenticarUsuarioUseCaseImpl;
import br.com.fiap.fasefood.core.usecase.gateways.UsuarioRepository;
import br.com.fiap.fasefood.core.usecase.interfaces.AlterarSenhaUsuarioUseCase;
import br.com.fiap.fasefood.core.usecase.interfaces.AutenticarUsuarioUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfig {

    @Bean
    public AutenticarUsuarioUseCase autenticarUsuarioUseCase(UsuarioRepository usuarioRepository) {
        return new AutenticarUsuarioUseCaseImpl(usuarioRepository);
    }

    @Bean
    public AlterarSenhaUsuarioUseCase alterarSenhaUseCase(UsuarioRepository usuarioRepository) {
        return new AlterarSenhaUseCaseImpl(usuarioRepository);
    }
}