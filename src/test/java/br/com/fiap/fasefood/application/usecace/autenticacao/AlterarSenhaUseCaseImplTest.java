package br.com.fiap.fasefood.application.usecace.autenticacao;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import br.com.fiap.fasefood.application.usecase.autenticacao.AlterarSenhaUseCaseImpl;
import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.UsuarioRepository;
import br.com.fiap.fasefood.infra.controller.dto.ChangeUserPasswordDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AlterarSenhaUseCaseImplTest {

    private AlterarSenhaUseCaseImpl alterarSenhaUseCase;
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;
    private ChangeUserPasswordDTO changePasswordDTO;

    private final static Long USER_ID = 1L;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        alterarSenhaUseCase = new AlterarSenhaUseCaseImpl(usuarioRepository);

        usuario = mock(Usuario.class);
        changePasswordDTO = new ChangeUserPasswordDTO("novaSenha123");
    }

    @Test
    void deveAlterarSenhaDoUsuarioQuandoUsuarioExiste() {
        when(usuarioRepository.findById(USER_ID)).thenReturn(Optional.of(usuario));

        alterarSenhaUseCase.alterarSenha(USER_ID, changePasswordDTO);

        verify(usuarioRepository).findById(USER_ID);
        verify(usuario).alterarSenha("novaSenha123");
        verify(usuarioRepository).salvar(usuario);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {

        when(usuarioRepository.findById(USER_ID)).thenReturn(Optional.empty());
        ResourceNotFoundException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> alterarSenhaUseCase.alterarSenha(USER_ID, changePasswordDTO)
        );
        assertEquals("Usuário não encontrado com ID: " + USER_ID, thrown.getMessage());
        verify(usuarioRepository).findById(USER_ID);
        verify(usuarioRepository, never()).salvar(any());
    }
}
