package br.com.fiap.fasefood.infra.controller;

import br.com.fiap.fasefood.AbstractIntegrationTest;
import br.com.fiap.fasefood.infra.controllers.dto.usuario.CreateUserDTO;
import br.com.fiap.fasefood.infra.controllers.dto.usuario.EnderecoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class UsuarioControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarUsuarioComSucesso() throws Exception {
        EnderecoDTO endereco = new EnderecoDTO(
                "Rua Teste", "123", "12345678", "Apto 101",
                "Bairro Teste", "Cidade Teste", "TS"
        );

        CreateUserDTO novoUsuario = new CreateUserDTO(
                "Usuário de Teste", "teste@fiap.com.br", "testuser",
                "senha123", "CLIENTE", endereco
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novoUsuario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("Usuário de Teste"))
                .andExpect(jsonPath("$.email").value("teste@fiap.com.br"));
    }
}