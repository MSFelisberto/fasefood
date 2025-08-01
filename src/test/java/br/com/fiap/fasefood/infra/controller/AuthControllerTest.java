package br.com.fiap.fasefood.infra.controller;

import br.com.fiap.fasefood.core.usecase.interfaces.AlterarSenhaUsuarioUseCase;
import br.com.fiap.fasefood.core.usecase.interfaces.AutenticarUsuarioUseCase;
import br.com.fiap.fasefood.infra.controller.dto.ChangeUserPasswordDTO;
import br.com.fiap.fasefood.infra.controller.dto.LoginRequestDTO;
import br.com.fiap.fasefood.infra.controller.dto.LoginResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    private AuthController authController;
    private AutenticarUsuarioUseCase autenticarUseCase;
    private AlterarSenhaUsuarioUseCase alterarSenhaUseCase;

    @BeforeEach
    public void setUp() {
        autenticarUseCase = mock(AutenticarUsuarioUseCase.class);
        alterarSenhaUseCase = mock(AlterarSenhaUsuarioUseCase.class);
        authController = new AuthController(autenticarUseCase,alterarSenhaUseCase);
    }

    @Test
    public void deveChamarLoginComSucesso(){
        LoginRequestDTO loginRequestDTO = mock(LoginRequestDTO.class);
        LoginResponseDTO loginResponseDTO = mock(LoginResponseDTO.class);
        when(autenticarUseCase.autenticar(loginRequestDTO)).thenReturn(loginResponseDTO);
        ResponseEntity<LoginResponseDTO> response= authController.login(loginRequestDTO);
        assertEquals(response.getBody(), loginResponseDTO);
        verify(autenticarUseCase,times(1)).autenticar(loginRequestDTO);
    }

    @Test
    public void deveAlterarSenhaComSucesso(){
        Long userID = 1L;
        ChangeUserPasswordDTO changeUserPasswordDTO = mock(ChangeUserPasswordDTO.class);
        doNothing().when(alterarSenhaUseCase).alterarSenha(userID, changeUserPasswordDTO);
        authController.alterarSenha(1L, changeUserPasswordDTO);
        verify(alterarSenhaUseCase,times(1)).alterarSenha(userID,changeUserPasswordDTO);
    }
}
