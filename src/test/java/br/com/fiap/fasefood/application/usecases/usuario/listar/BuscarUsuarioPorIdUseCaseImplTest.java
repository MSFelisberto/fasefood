package br.com.fiap.fasefood.application.usecases.usuario.listar;

import br.com.fiap.fasefood.core.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.gateways.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BuscarUsuarioPorIdUseCaseImplTest {

    private BuscarUsuarioPorIdUseCaseImpl buscarUsuarioPorIdUseCase;
    private UsuarioRepository usuarioRepository;

    private static final Long USUARIO_ID = 1L;
    @BeforeEach
    public void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        buscarUsuarioPorIdUseCase = new BuscarUsuarioPorIdUseCaseImpl(usuarioRepository);
    }

    @Test
    public void deveRetornarUsuarioComSucesso(){

        Usuario usuario = mock(Usuario.class);
        when(usuarioRepository.findById(USUARIO_ID)).thenReturn(Optional.of(usuario));

        ListUserOutput response = buscarUsuarioPorIdUseCase.buscarPorId(USUARIO_ID);

        assertNotNull(response);
        verify(usuarioRepository,times(1)).findById(USUARIO_ID);


    }

    @Test
    public void deveLancarExcecaoAoNaoEncontrarUsuario(){
        when(usuarioRepository.findById(USUARIO_ID)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, ()-> buscarUsuarioPorIdUseCase.buscarPorId(USUARIO_ID));
        assertEquals("Usuário não encontrado com ID: 1", exception.getMessage());

    }
}
