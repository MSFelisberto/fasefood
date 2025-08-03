package br.com.fiap.fasefood.infra.config;

import br.com.fiap.fasefood.application.usecases.usuario.criar.CriarUsuarioUseCaseImpl;
import br.com.fiap.fasefood.application.usecases.usuario.deletar.DeletarUsuarioUseCaseImpl;
import br.com.fiap.fasefood.application.usecases.usuario.listar.BuscarUsuarioPorIdUseCaseImpl;
import br.com.fiap.fasefood.application.usecases.usuario.listar.ListarTodosUsuariosUseCaseImpl;
import br.com.fiap.fasefood.core.gateways.TipoUsuarioRepository;
import br.com.fiap.fasefood.core.gateways.UsuarioRepository;
import br.com.fiap.fasefood.infra.usecases.decorators.usuario.TransactionalAlterarTipoUsuarioUseCase;
import br.com.fiap.fasefood.infra.usecases.decorators.usuario.TransactionalAtualizarUsuarioUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UsuarioConfigTest {

    private UsuarioRepository usuarioRepository;
    private TipoUsuarioRepository tipoUsuarioRepository;
    private UsuarioConfig config;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        tipoUsuarioRepository = mock(TipoUsuarioRepository.class);
        config = new UsuarioConfig();
    }
    @Test
    void deveRetornarCriarUsuarioUseCase() {
        var useCase = config.criarUsuarioUseCase(usuarioRepository, tipoUsuarioRepository);

        assertNotNull(useCase);
        assertInstanceOf(CriarUsuarioUseCaseImpl.class, useCase);
    }

    @Test
    void deveRetornarAtualizarUsuarioUseCase() {
        var useCase = config.atualizarUsuarioUseCase(usuarioRepository);

        assertNotNull(useCase);
        assertInstanceOf(TransactionalAtualizarUsuarioUseCase.class, useCase);
    }

    @Test
    void deveRetornarDeletarUsuarioUseCase() {
        var useCase = config.deletarUsuarioUseCase(usuarioRepository);

        assertNotNull(useCase);
        assertInstanceOf(DeletarUsuarioUseCaseImpl.class, useCase);
    }

    @Test
    void deveRetornarBuscarUsuarioPorIdUseCase() {
        var useCase = config.buscarUsuarioPorIdUseCase(usuarioRepository);

        assertNotNull(useCase);
        assertInstanceOf(BuscarUsuarioPorIdUseCaseImpl.class, useCase);
    }

    @Test
    void deveRetornarListarTodosUsuariosUseCase() {
        var useCase = config.listarTodosUsuariosUseCase(usuarioRepository);

        assertNotNull(useCase);
        assertInstanceOf(ListarTodosUsuariosUseCaseImpl.class, useCase);
    }

    @Test
    void deveRetornarAlterarTipoUsuarioUseCase() {
        var useCase = config.alterarTipoUsuarioUseCase(usuarioRepository, tipoUsuarioRepository);

        assertNotNull(useCase);
        assertInstanceOf(TransactionalAlterarTipoUsuarioUseCase.class, useCase);
    }
}
