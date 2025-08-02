package br.com.fiap.fasefood.application.usecace.autenticacao;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import br.com.fiap.fasefood.application.usecases.autenticacao.atualizar.AlterarSenhaUseCaseImpl;
import br.com.fiap.fasefood.core.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.gateways.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AlterarSenhaUseCaseImplTest {

    @InjectMocks
    private AlterarSenhaUseCaseImpl alterarSenhaUseCase;

    @Mock
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;
    private String senha;

    @BeforeEach
    void setUp() {
        usuario = mock(Usuario.class);
        senha = "novaSenha123";
    }

    @Test
    void deveAlterarSenhaDoUsuarioQuandoUsuarioExiste() {
        Long userId = 1L;

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        alterarSenhaUseCase.alterarSenha(userId, senha);

        verify(usuarioRepository).findById(userId);
        verify(usuario).alterarSenha("novaSenha123");
        verify(usuarioRepository).salvar(usuario);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        Long userId = 1L;

        when(usuarioRepository.findById(userId)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> alterarSenhaUseCase.alterarSenha(userId, senha)
        );

        assertEquals("Usuário não encontrado com ID: " + userId, thrown.getMessage());
        verify(usuarioRepository).findById(userId);
        verify(usuarioRepository, never()).salvar(any());
    }
}
