package br.com.fiap.fasefood.application.usecases.autenticacao.autenticar;

import br.com.fiap.fasefood.infra.controllers.dto.LoginRequestDTO;
import br.com.fiap.fasefood.infra.controllers.dto.LoginResponseDTO;

public interface AutenticarUsuarioUseCase {
    LoginResponseDTO autenticar(LoginRequestDTO loginRequest);
}