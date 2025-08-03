package br.com.fiap.fasefood.infra.controller;

import br.com.fiap.fasefood.AbstractIntegrationTest;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CreateCardapioDTO;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.ItensCreateCardapioItemDTO;
import br.com.fiap.fasefood.infra.controllers.dto.restaurante.CreateRestauranteDTO;
import br.com.fiap.fasefood.infra.controllers.dto.usuario.CreateUserDTO;
import br.com.fiap.fasefood.infra.controllers.dto.usuario.EnderecoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class CardapioControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long restauranteId;

    @BeforeEach
    void setup() throws Exception {
        EnderecoDTO enderecoDono = new EnderecoDTO("Av. Principal", "100", "87654321", null, "Centro", "Cidade Dono", "TD");
        CreateUserDTO donoDTO = new CreateUserDTO("Dono Cardapio", "dono.cardapio@rest.com", "donocardapio", "senhaforte", "DONO_RESTAURANTE", enderecoDono);

        MvcResult resultDono = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(donoDTO)))
                .andExpect(status().isCreated())
                .andReturn();
        Long donoId = objectMapper.readTree(resultDono.getResponse().getContentAsString()).get("id").asLong();

        EnderecoDTO enderecoRestaurante = new EnderecoDTO("Rua do Cardapio", "99", "55443322", null, "Gourmet", "Cidade Cardapio", "TC");
        CreateRestauranteDTO restauranteDTO = new CreateRestauranteDTO("Restaurante do Cardapio", enderecoRestaurante, "Variada", LocalTime.of(10, 0), LocalTime.of(23, 0), donoId);

        MvcResult resultRestaurante = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restauranteDTO)))
                .andExpect(status().isCreated())
                .andReturn();
        this.restauranteId = objectMapper.readTree(resultRestaurante.getResponse().getContentAsString()).get("id").asLong();
    }

    @Test
    void deveCriarCardapioComSucesso() throws Exception {
        ItensCreateCardapioItemDTO item = new ItensCreateCardapioItemDTO("Prato Teste", "Descricao do prato", new BigDecimal("25.50"), false, "/fotos/prato.jpg");
        CreateCardapioDTO cardapioDTO = new CreateCardapioDTO(restauranteId, "Cardápio de Almoço", "Opções para o almoço", Collections.singletonList(item));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cardapios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cardapioDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void deveListarCardapiosDoRestaurante() throws Exception {
        CreateCardapioDTO cardapioDTO = new CreateCardapioDTO(restauranteId, "Cardápio de Jantar", "Opções para o jantar", Collections.emptyList());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cardapios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cardapioDTO)));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cardapios/restaurante/{restauranteId}", restauranteId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].nome").value("Cardápio de Jantar"))
                .andExpect(jsonPath("$.totalElements").value(1));
    }
}