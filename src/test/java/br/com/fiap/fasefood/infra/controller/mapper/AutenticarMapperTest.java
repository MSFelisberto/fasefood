package br.com.fiap.fasefood.infra.controller.mapper;

import br.com.fiap.fasefood.application.usecases.autenticacao.autenticar.LoginRequestInput;
import br.com.fiap.fasefood.application.usecases.autenticacao.autenticar.LoginResponseOutput;
import br.com.fiap.fasefood.infra.controllers.dto.autenticacao.LoginRequestDTO;
import br.com.fiap.fasefood.infra.controllers.dto.autenticacao.LoginResponseDTO;
import br.com.fiap.fasefood.infra.controllers.mapper.autenticar.AutenticarMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AutenticarMapperTest {

    @Test
    void deveConverterLoginRequestDTOParaLoginRequestInput() {

        LoginRequestDTO dto = new LoginRequestDTO("usuario", "senha");

        LoginRequestInput input = AutenticarMapper.toRequestInput(dto);

        assertEquals("usuario", input.login());
        assertEquals("senha", input.senha());
    }

    @Test
    void deveConverterLoginResponseOutputParaLoginResponseDTO() {
        LoginResponseOutput output = new LoginResponseOutput(true, "Autenticado com sucesso");

        LoginResponseDTO dto = AutenticarMapper.toResponseDTO(output);

        assertTrue(dto.sucesso());
        assertEquals("Autenticado com sucesso", dto.mensagem());
    }
}
