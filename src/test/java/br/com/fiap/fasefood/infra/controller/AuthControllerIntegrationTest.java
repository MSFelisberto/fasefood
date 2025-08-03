package br.com.fiap.fasefood.infra.controller;

import br.com.fiap.fasefood.AbstractIntegrationTest;
import br.com.fiap.fasefood.infra.controllers.dto.autenticacao.LoginRequestDTO;
import br.com.fiap.fasefood.infra.controllers.dto.usuario.ChangeUserPasswordDTO;
import br.com.fiap.fasefood.infra.controllers.dto.usuario.CreateUserDTO;
import br.com.fiap.fasefood.infra.controllers.dto.usuario.EnderecoDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class AuthControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private CreateUserDTO criarUsuarioParaTeste() {
        EnderecoDTO endereco = new EnderecoDTO("Rua Login", "404", "88888777", null, "Auth", "Cidade Teste", "AT");
        return new CreateUserDTO("Usuario Auth", "auth@fiap.com", "authuser", "senha123", "CLIENTE", endereco);
    }

    @Test
    void deveAutenticarUsuarioComSucesso() throws Exception {
        CreateUserDTO usuario = criarUsuarioParaTeste();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isCreated());

        LoginRequestDTO loginRequest = new LoginRequestDTO("authuser", "senha123");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sucesso").value(true))
                .andExpect(jsonPath("$.mensagem").value("Login realizado com sucesso"));
    }

    @Test
    void deveFalharAutenticacaoComSenhaIncorreta() throws Exception {
        CreateUserDTO usuario = criarUsuarioParaTeste();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isCreated());

        LoginRequestDTO loginRequest = new LoginRequestDTO("authuser", "senha_errada");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized()); // Espera 401 Unauthorized
    }

    @Test
    void deveAlterarSenhaComSucesso() throws Exception {
        CreateUserDTO usuario = criarUsuarioParaTeste();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isCreated())
                .andReturn();
        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        Long usuarioId = jsonNode.get("id").asLong();

        ChangeUserPasswordDTO novaSenhaRequest = new ChangeUserPasswordDTO("novaSenha456");
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/auth/{id}/password", usuarioId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novaSenhaRequest)))
                .andExpect(status().isNoContent());

        LoginRequestDTO loginComNovaSenha = new LoginRequestDTO("authuser", "novaSenha456");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginComNovaSenha)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sucesso").value(true));
    }
}