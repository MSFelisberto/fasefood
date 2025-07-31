package br.com.fiap.fasefood.infra.config;

import br.com.fiap.fasefood.application.usecases.usuario.alterar.AlterarTipoUsuarioUseCase;
import br.com.fiap.fasefood.application.usecases.usuario.alterar.AlterarTipoUsuarioUseCaseImpl;
import br.com.fiap.fasefood.application.usecases.usuario.alterar.AtualizarUsuarioUseCase;
import br.com.fiap.fasefood.application.usecases.usuario.alterar.AtualizarUsuarioUseCaseImpl;
import br.com.fiap.fasefood.application.usecases.usuario.criar.CriarUsuarioUseCase;
import br.com.fiap.fasefood.application.usecases.usuario.criar.CriarUsuarioUseCaseImpl;
import br.com.fiap.fasefood.application.usecases.usuario.deletar.DeletarUsuarioUseCase;
import br.com.fiap.fasefood.application.usecases.usuario.deletar.DeletarUsuarioUseCaseImpl;
import br.com.fiap.fasefood.application.usecases.usuario.listar.BuscarUsuarioPorIdUseCase;
import br.com.fiap.fasefood.application.usecases.usuario.listar.BuscarUsuarioPorIdUseCaseImpl;
import br.com.fiap.fasefood.application.usecases.usuario.listar.ListarTodosUsuariosUseCase;
import br.com.fiap.fasefood.application.usecases.usuario.listar.ListarTodosUsuariosUseCaseImpl;
import br.com.fiap.fasefood.core.gateways.TipoUsuarioRepository;
import br.com.fiap.fasefood.core.gateways.UsuarioRepository;
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
