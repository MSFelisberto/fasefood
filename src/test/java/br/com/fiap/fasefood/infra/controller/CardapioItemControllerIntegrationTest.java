package br.com.fiap.fasefood.infra.controller;

import br.com.fiap.fasefood.AbstractIntegrationTest;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CreateCardapioDTO;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CreateCardapioItemDTO;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class CardapioItemControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long cardapioId;

    @BeforeEach
    void setup() throws Exception {
        EnderecoDTO enderecoDono = new EnderecoDTO("Av. Itens", "200", "12121212", null, "Item", "Cidade Item", "IT");
        CreateUserDTO donoDTO = new CreateUserDTO("Dono Item",
                "dono.item@rest.com", "donoitem", "senhaforte", "DONO_RESTAURANTE", enderecoDono);
        MvcResult resultDono = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(donoDTO))).andReturn();
        Long donoId = objectMapper.readTree(resultDono.getResponse().getContentAsString()).get("id").asLong();

        EnderecoDTO enderecoRestaurante = new EnderecoDTO("Rua do Item", "199", "34343434", null, "Prato", "Cidade Item", "IT");
        CreateRestauranteDTO restauranteDTO = new CreateRestauranteDTO("Restaurante dos Itens",
                enderecoRestaurante, "A La Carte", LocalTime.of(12, 0), LocalTime.of(20, 0), donoId);
        MvcResult resultRestaurante = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/restaurantes")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(restauranteDTO))).andReturn();
        Long restauranteId = objectMapper.readTree(resultRestaurante.getResponse().getContentAsString()).get("id").asLong();

        CreateCardapioDTO cardapioDTO = new CreateCardapioDTO(restauranteId, "Cardápio Principal", "Todos os itens", Collections.emptyList());
        MvcResult resultCardapio = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cardapios")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(cardapioDTO))).andReturn();
        this.cardapioId = objectMapper.readTree(resultCardapio.getResponse().getContentAsString()).get("id").asLong();
    }

    @Test
    void deveCriarItemNoCardapioComSucesso() throws Exception {
        CreateCardapioItemDTO itemDTO = new CreateCardapioItemDTO(
                cardapioId,
                "Bife à Parmegiana",
                "Bife empanado com queijo e molho",
                new BigDecimal("55.90"),
                false,
                "/fotos/parmegiana.jpg"
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/item-cardapio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("Bife à Parmegiana"))
                .andExpect(jsonPath("$.cardapioId").value(cardapioId));
    }

    @Test
    void deveListarItensDoCardapio() throws Exception {
        CreateCardapioItemDTO itemDTO = new CreateCardapioItemDTO(cardapioId, "Feijoada", "Completa",
                new BigDecimal("80.00"), true, "/fotos/feijoada.jpg");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/item-cardapio")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(itemDTO)));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/item-cardapio/cardapio/{cardapioId}", cardapioId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].nome").value("Feijoada"))
                .andExpect(jsonPath("$.totalElements").value(1));
    }
}