package br.com.fiap.fasefood.application.usecase.autenticacao.autenticar;

import br.com.fiap.fasefood.infra.controller.dto.LoginRequestDTO;
import br.com.fiap.fasefood.infra.controller.dto.LoginResponseDTO;

public interface AutenticarUsuarioUseCase {
    LoginResponseDTO autenticar(LoginRequestDTO loginRequest);
}