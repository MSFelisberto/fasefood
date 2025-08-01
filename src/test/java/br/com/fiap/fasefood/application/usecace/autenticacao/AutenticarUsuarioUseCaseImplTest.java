package br.com.fiap.fasefood.application.usecace.autenticacao;


import br.com.fiap.fasefood.application.usecase.autenticacao.AutenticarUsuarioUseCaseImpl;
import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.AuthenticationFailedException;
import br.com.fiap.fasefood.core.usecase.gateways.UsuarioRepository;
import br.com.fiap.fasefood.infra.controller.dto.LoginRequestDTO;
import br.com.fiap.fasefood.infra.controller.dto.LoginResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AutenticarUsuarioUseCaseImplTest {

    private AutenticarUsuarioUseCaseImpl autenticarUsuario;
    private UsuarioRepository usuarioRepository;


    private LoginRequestDTO loginRequest;
    private Usuario usuario;

    private static final String SENHA_IGUAIS = "1010";
    private static final String SENHA_DIFERENTE = "10";

    @BeforeEach
    public void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        autenticarUsuario = new AutenticarUsuarioUseCaseImpl(usuarioRepository);

        loginRequest = mock(LoginRequestDTO.class);
        usuario = mock(Usuario.class);
    }

    @Test
    public void deveAutenticarComSucesso() {
        when(usuarioRepository.findByLogin(loginRequest.login())).thenReturn(Optional.of(usuario));
        when(usuario.getSenha()).thenReturn(SENHA_IGUAIS);
        when(loginRequest.senha()).thenReturn(SENHA_IGUAIS);

        LoginResponseDTO response = autenticarUsuario.autenticar(loginRequest);

        assertTrue(response.sucesso());
        assertEquals("Login realizado com sucesso", response.mensagem());
    }

    @Test
    public void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        when(usuarioRepository.findByLogin(loginRequest.login())).thenReturn(Optional.empty());

        AuthenticationFailedException exception = assertThrows(AuthenticationFailedException.class,
                () -> autenticarUsuario.autenticar(loginRequest));

        assertEquals("Login ou senha incorretos", exception.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoSenhaIncorreta() {
        when(usuarioRepository.findByLogin(loginRequest.login())).thenReturn(Optional.of(usuario));
        when(usuario.getSenha()).thenReturn(SENHA_IGUAIS);
        when(loginRequest.senha()).thenReturn(SENHA_DIFERENTE);

        AuthenticationFailedException exception = assertThrows(AuthenticationFailedException.class,
                () -> autenticarUsuario.autenticar(loginRequest));

        assertEquals("Login ou senha incorretos", exception.getMessage());
    }
}