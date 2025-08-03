package br.com.fiap.fasefood.application.usecases.usuario.alterar;

import br.com.fiap.fasefood.application.usecases.shared.endereco.EnderecoInput;
import br.com.fiap.fasefood.application.usecases.usuario.listar.ListUserOutput;
import br.com.fiap.fasefood.core.entities.Endereco;
import br.com.fiap.fasefood.core.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.gateways.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class AtualizarUsuarioUseCaseImplTest {

    private AtualizarUsuarioUseCaseImpl atualizarUsuarioUseCase;
    private UsuarioRepository usuarioRepository;

    private UpdateUserDataInput updateUserDataDTO;

    private static final Long USUARIO_ID = 1L;

    @BeforeEach
    public void setUp() {

        usuarioRepository = mock(UsuarioRepository.class);
        atualizarUsuarioUseCase = new AtualizarUsuarioUseCaseImpl(usuarioRepository);

        updateUserDataDTO = new UpdateUserDataInput("Nome", "fiap@fiap.com.br", mock(EnderecoInput.class));
    }

    @Test
    public void deveAtualizarUsuarioComSucesso(){
        Usuario usuario = mock(Usuario.class);
        Endereco endereco = mock(Endereco.class);
        Usuario usuarioSalvo = mock(Usuario.class);

        when(usuarioRepository.findById(USUARIO_ID)).thenReturn(Optional.of(usuario));
        when(usuario.getEndereco()).thenReturn(endereco);
        when(usuarioRepository.salvar(usuario)).thenReturn(usuarioSalvo);

        ListUserOutput response = atualizarUsuarioUseCase.atualizar(USUARIO_ID, updateUserDataDTO);

        assertNotNull(response);
        verify(usuarioRepository, times(1)).salvar(usuario);


    }

    @Test
    public void deveLancarExcecaoUsuarioNaoEncontrado(){

        when(usuarioRepository.findById(USUARIO_ID)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> atualizarUsuarioUseCase.atualizar(USUARIO_ID, updateUserDataDTO));
        assertEquals("Usuário não encontrado com ID: 1", exception.getMessage());
    }

}
