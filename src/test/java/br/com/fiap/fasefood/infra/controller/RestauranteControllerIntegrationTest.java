package br.com.fiap.fasefood.infra.controller;

import br.com.fiap.fasefood.AbstractIntegrationTest;
import br.com.fiap.fasefood.infra.controllers.dto.restaurante.CreateRestauranteDTO;
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

import java.time.LocalTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class RestauranteControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarRestauranteComSucesso() throws Exception {
        EnderecoDTO enderecoDono = new EnderecoDTO("Av. Principal", "100", "87654321", null, "Centro", "Cidade Dono", "TD");
        CreateUserDTO donoDTO = new CreateUserDTO("Dono do Restaurante", "dono@rest.com", "dono", "senhaforte", "DONO_RESTAURANTE", enderecoDono);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(donoDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        Long donoId = jsonNode.get("id").asLong();

        EnderecoDTO enderecoRestaurante = new EnderecoDTO("Rua da Comida", "50", "11223344", null, "Gourmet", "Cidade Restaurante", "TR");
        CreateRestauranteDTO restauranteDTO = new CreateRestauranteDTO(
                "Restaurante Saboroso",
                enderecoRestaurante,
                "Brasileira",
                LocalTime.of(11, 30),
                LocalTime.of(22, 0),
                donoId
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restauranteDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("Restaurante Saboroso"))
                .andExpect(jsonPath("$.dono.id").value(donoId));
    }
}