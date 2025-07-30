package br.com.fiap.fasefood.config;

import br.com.fiap.fasefood.application.usecase.usuario.alterar.AlterarTipoUsuarioUseCase;
import br.com.fiap.fasefood.application.usecase.usuario.alterar.AlterarTipoUsuarioUseCaseImpl;
import br.com.fiap.fasefood.application.usecase.usuario.alterar.AtualizarUsuarioUseCase;
import br.com.fiap.fasefood.application.usecase.usuario.alterar.AtualizarUsuarioUseCaseImpl;
import br.com.fiap.fasefood.application.usecase.usuario.criar.CriarUsuarioUseCase;
import br.com.fiap.fasefood.application.usecase.usuario.criar.CriarUsuarioUseCaseImpl;
import br.com.fiap.fasefood.application.usecase.usuario.deletar.DeletarUsuarioUseCase;
import br.com.fiap.fasefood.application.usecase.usuario.deletar.DeletarUsuarioUseCaseImpl;
import br.com.fiap.fasefood.application.usecase.usuario.listar.BuscarUsuarioPorIdUseCase;
import br.com.fiap.fasefood.application.usecase.usuario.listar.BuscarUsuarioPorIdUseCaseImpl;
import br.com.fiap.fasefood.application.usecase.usuario.listar.ListarTodosUsuariosUseCase;
import br.com.fiap.fasefood.application.usecase.usuario.listar.ListarTodosUsuariosUseCaseImpl;
import br.com.fiap.fasefood.core.usecase.gateways.TipoUsuarioRepository;
import br.com.fiap.fasefood.core.usecase.gateways.UsuarioRepository;
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
