package br.com.fiap.fasefood.infra.controller;

import br.com.fiap.fasefood.application.usecases.autenticacao.atualizar.AlterarSenhaUsuarioUseCase;
import br.com.fiap.fasefood.application.usecases.autenticacao.autenticar.AutenticarUsuarioUseCase;
import br.com.fiap.fasefood.application.usecases.autenticacao.autenticar.LoginRequestInput;
import br.com.fiap.fasefood.application.usecases.autenticacao.autenticar.LoginResponseOutput;
import br.com.fiap.fasefood.infra.controllers.AuthController;
import br.com.fiap.fasefood.infra.controllers.dto.autenticacao.LoginRequestDTO;
import br.com.fiap.fasefood.infra.controllers.dto.autenticacao.LoginResponseDTO;
import br.com.fiap.fasefood.infra.controllers.dto.usuario.ChangeUserPasswordDTO;
import br.com.fiap.fasefood.infra.controllers.mapper.autenticar.AutenticarMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockStatic;
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
        try(MockedStatic<AutenticarMapper> autenticarMapper = mockStatic(AutenticarMapper.class)) {
            LoginRequestInput loginRequestInput = new LoginRequestInput("teste", "senha");
            LoginRequestDTO loginRequestDTO = new LoginRequestDTO("teste", "senha");

            LoginResponseOutput loginResponseOutput = new LoginResponseOutput(true, "sucesso");
            LoginResponseDTO loginResponseDTO = new LoginResponseDTO(true, "sucesso");

            autenticarMapper.when(()-> AutenticarMapper.toRequestInput(loginRequestDTO)).thenReturn(loginRequestInput);
            when(autenticarUseCase.autenticar(loginRequestInput)).thenReturn(loginResponseOutput);
            autenticarMapper.when(()-> AutenticarMapper.toResponseDTO(loginResponseOutput)).thenReturn(loginResponseDTO);

            ResponseEntity<LoginResponseDTO> response = authController.login(loginRequestDTO);

            assertEquals(response.getBody(), loginResponseDTO);
            verify(autenticarUseCase, times(1)).autenticar(loginRequestInput);
        }
    }

    @Test
    public void deveAlterarSenhaComSucesso(){
        Long userID = 1L;
        ChangeUserPasswordDTO changeUserPasswordDTO = new ChangeUserPasswordDTO("teste");
        doNothing().when(alterarSenhaUseCase).alterarSenha(userID, changeUserPasswordDTO.senha());
        authController.alterarSenha(1L, changeUserPasswordDTO);
        verify(alterarSenhaUseCase,times(1)).alterarSenha(userID,changeUserPasswordDTO.senha());
    }
}
