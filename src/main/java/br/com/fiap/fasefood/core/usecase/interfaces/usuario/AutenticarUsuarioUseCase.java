package br.com.fiap.fasefood.core.usecase.interfaces.usuario;

import br.com.fiap.fasefood.infra.controller.dto.LoginRequestDTO;
import br.com.fiap.fasefood.infra.controller.dto.LoginResponseDTO;

public interface AutenticarUsuarioUseCase {
    LoginResponseDTO autenticar(LoginRequestDTO loginRequest);
}