package br.com.fiap.fasefood.config;

import br.com.fiap.fasefood.application.usecase.autenticacao.AlterarSenhaUseCaseImpl;
import br.com.fiap.fasefood.application.usecase.autenticacao.AutenticarUsuarioUseCaseImpl;
import br.com.fiap.fasefood.application.usecase.usuario.*;
import br.com.fiap.fasefood.core.usecase.gateways.UsuarioRepository;
import br.com.fiap.fasefood.core.usecase.interfaces.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsuarioConfig {

    @Bean
    public CriarUsuarioUseCase criarUsuarioUseCase(UsuarioRepository usuarioRepository) {
        return new CriarUsuarioUseCaseImpl(usuarioRepository);
    };

    @Bean
    public AtualizarUsuarioUseCase atualizarUsuarioUseCase(UsuarioRepository usuarioRepository) {
        return new AtualizarUsuarioUseCaseImpl(usuarioRepository);
    }

    @Bean
    public DeletarUsuarioUseCase deletarUsuarioUseCase(UsuarioRepository usuarioRepository) {
        return new DeletarUsuarioUseCaseImpl(usuarioRepository);
    }

    @Bean
    public BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase(UsuarioRepository usuarioRepository) {
        return new BuscarUsuarioPorIdUseCaseImpl(usuarioRepository);
    }

    @Bean
    public ListarTodosUsuariosUseCase listarTodosUsuariosUseCase(UsuarioRepository usuarioRepository) {
        return new ListarTodosUsuariosUseCaseImpl(usuarioRepository);
    }
}
