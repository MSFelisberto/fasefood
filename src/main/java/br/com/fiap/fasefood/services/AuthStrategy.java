package br.com.fiap.fasefood.services;

import br.com.fiap.fasefood.dtos.ChangeUserPasswordDTO;
import br.com.fiap.fasefood.dtos.LoginRequestDTO;
import br.com.fiap.fasefood.dtos.LoginResponseDTO;

public interface AuthStrategy {

    LoginResponseDTO authenticate(LoginRequestDTO loginRequestDTO);

    void changeUserPassword(Long id, ChangeUserPasswordDTO userData);
}
