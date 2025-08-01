package br.com.fiap.fasefood.application.usecace.usuario;

import br.com.fiap.fasefood.application.usecase.usuario.DeletarUsuarioUseCaseImpl;
import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class DeletarUsuarioUseCaseImplTest {

    private DeletarUsuarioUseCaseImpl deletarUsuarioUseCase;
    private UsuarioRepository usuarioRepository;

    private static final Long USUARIO_ID = 1L;

    @BeforeEach
    public void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        deletarUsuarioUseCase = new DeletarUsuarioUseCaseImpl(usuarioRepository);
    }

    @Test
    public void deveDeletarUsuarioComSucesso(){

        Usuario usuario = mock(Usuario.class);
        when(usuarioRepository.findById(USUARIO_ID)).thenReturn(Optional.of(usuario));
        deletarUsuarioUseCase.deletar(USUARIO_ID);

        verify(usuarioRepository,times(1)).salvar(usuario);
    }

    @Test
    public void deveLancarExcecaoQuandoNaoEncontrarUsuario(){

        when(usuarioRepository.findById(USUARIO_ID)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, ()-> deletarUsuarioUseCase.deletar(USUARIO_ID));
        assertEquals("Usuário não encontrado com ID: 1", exception.getMessage());

    }
}
