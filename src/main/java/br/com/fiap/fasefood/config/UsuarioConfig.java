package br.com.fiap.fasefood.config;

import br.com.fiap.fasefood.application.usecase.usuario.*;
import br.com.fiap.fasefood.core.usecase.gateways.TipoUsuarioRepository;
import br.com.fiap.fasefood.core.usecase.gateways.UsuarioRepository;
import br.com.fiap.fasefood.core.usecase.interfaces.usuario.AlterarTipoUsuarioUseCase;
import br.com.fiap.fasefood.core.usecase.interfaces.usuario.AtualizarUsuarioUseCase;
import br.com.fiap.fasefood.core.usecase.interfaces.usuario.BuscarUsuarioPorIdUseCase;
import br.com.fiap.fasefood.core.usecase.interfaces.usuario.CriarUsuarioUseCase;
import br.com.fiap.fasefood.core.usecase.interfaces.usuario.DeletarUsuarioUseCase;
import br.com.fiap.fasefood.core.usecase.interfaces.usuario.ListarTodosUsuariosUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsuarioConfig {

    @Bean
    public CriarUsuarioUseCase criarUsuarioUseCase(UsuarioRepository usuarioRepository, TipoUsuarioRepository tipoUsuarioRepository) {
        return new CriarUsuarioUseCaseImpl(usuarioRepository, tipoUsuarioRepository);
    }

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

    @Bean
    public AlterarTipoUsuarioUseCase alterarTipoUsuarioUseCase(UsuarioRepository usuarioRepository, TipoUsuarioRepository tipoUsuarioRepository) {
        return new AlterarTipoUsuarioUseCaseImpl(usuarioRepository, tipoUsuarioRepository);
    }
}
