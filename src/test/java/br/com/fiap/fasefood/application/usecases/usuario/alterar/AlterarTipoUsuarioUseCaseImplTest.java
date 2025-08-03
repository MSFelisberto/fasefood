package br.com.fiap.fasefood.application.usecases.usuario.alterar;

import br.com.fiap.fasefood.application.usecases.usuario.listar.ListUserOutput;
import br.com.fiap.fasefood.core.entities.TipoUsuario;
import br.com.fiap.fasefood.core.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.gateways.TipoUsuarioRepository;
import br.com.fiap.fasefood.core.gateways.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AlterarTipoUsuarioUseCaseImplTest {

    private AlterarTipoUsuarioUseCaseImpl alterarTipoUsuarioUseCase;
    private UsuarioRepository usuarioRepository;
    private TipoUsuarioRepository tipoUsuarioRepository;

    private UpdateUserTypeInput updateUserTypeInput;
    private Usuario usuario;
    private static final Long USUARIO_ID = 1L;

    @BeforeEach
    public void setUp() {

        usuarioRepository = mock(UsuarioRepository.class);
        tipoUsuarioRepository = mock(TipoUsuarioRepository.class);
        alterarTipoUsuarioUseCase = new AlterarTipoUsuarioUseCaseImpl(usuarioRepository,tipoUsuarioRepository);

        updateUserTypeInput = new UpdateUserTypeInput(1L);
        usuario = mock(Usuario.class);
    }

    @Test
    public void deveAlterarTipoUsuarioComSucesso() {

//        ListUserDTO listUserDTO = mock(ListUserDTO.class);
        TipoUsuario novoTipoUsuario = mock(TipoUsuario.class);
        Usuario usuarioAtualizado = mock(Usuario.class);

        when(usuarioRepository.findById(USUARIO_ID)).thenReturn(Optional.of(usuario));
        when(tipoUsuarioRepository.findById(updateUserTypeInput.tipoUsuarioId())).thenReturn(Optional.of(novoTipoUsuario));
        when(usuarioRepository.salvar(usuario)).thenReturn(usuarioAtualizado);

        ListUserOutput response = alterarTipoUsuarioUseCase.alterarTipoUsuario(USUARIO_ID, updateUserTypeInput);

        assertNotNull(response);
        verify(usuarioRepository, times(1)).salvar(usuario);


    }

    @Test
    public void deveLancarExcecaoQuandoNaoEncontrarUsuario() {

        when(usuarioRepository.findById(USUARIO_ID)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> alterarTipoUsuarioUseCase.alterarTipoUsuario(USUARIO_ID, updateUserTypeInput));
        assertEquals("Usuário não encontrado com ID: 1", exception.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoNaoEncontrarTipoUsuario() {

        when(usuarioRepository.findById(USUARIO_ID)).thenReturn(Optional.of(usuario));
        when(tipoUsuarioRepository.findById(updateUserTypeInput.tipoUsuarioId())).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,() -> alterarTipoUsuarioUseCase.alterarTipoUsuario(USUARIO_ID, updateUserTypeInput));
        assertEquals("Tipo de usuário não encontrado com ID: 1", exception.getMessage());
    }

}
