package br.com.fiap.fasefood.infra.controllers.mapper.autenticar;

import br.com.fiap.fasefood.application.usecases.autenticacao.autenticar.LoginRequestInput;
import br.com.fiap.fasefood.application.usecases.autenticacao.autenticar.LoginResponseOutput;
import br.com.fiap.fasefood.infra.controllers.dto.autenticacao.LoginRequestDTO;
import br.com.fiap.fasefood.infra.controllers.dto.autenticacao.LoginResponseDTO;

public class AutenticarMapper {
    public static LoginRequestInput toRequestInput(LoginRequestDTO dto) {
        return new LoginRequestInput(
                dto.login(),
                dto.senha()
        );
    }

    public static LoginResponseDTO toResponseDTO(LoginResponseOutput data) {
        return new LoginResponseDTO(
                data.sucesso(),
                data.mensagem()
        );
    }
}
