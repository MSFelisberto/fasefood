package br.com.fiap.fasefood.infra.controller;

import br.com.fiap.fasefood.core.usecase.interfaces.restaurante.AtualizarRestauranteUseCase;
import br.com.fiap.fasefood.core.usecase.interfaces.restaurante.BuscarRestaurantePorIdUseCase;
import br.com.fiap.fasefood.core.usecase.interfaces.restaurante.CriarRestauranteUseCase;
import br.com.fiap.fasefood.core.usecase.interfaces.restaurante.DeletarRestauranteUseCase;
import br.com.fiap.fasefood.core.usecase.interfaces.restaurante.ListarRestaurantesUseCase;
import br.com.fiap.fasefood.infra.controller.dto.restaurante.CreateRestauranteDTO;
import br.com.fiap.fasefood.infra.controller.dto.restaurante.RestauranteResponseDTO;
import br.com.fiap.fasefood.infra.controller.dto.restaurante.UpdateRestauranteDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RestauranteControllerTest {


    private RestauranteController restauranteController;

    private CriarRestauranteUseCase criarRestauranteUseCase;
    private AtualizarRestauranteUseCase atualizarRestauranteUseCase;
    private DeletarRestauranteUseCase deletarRestauranteUseCase;
    private BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase;
    private ListarRestaurantesUseCase listarRestaurantesUseCase;

    private RestauranteResponseDTO restauranteResponseDTO ;
    private static final Long RESTAURANTE_ID = 1L;
    @BeforeEach
    public void setUp() {

        criarRestauranteUseCase = mock(CriarRestauranteUseCase.class);
        atualizarRestauranteUseCase = mock(AtualizarRestauranteUseCase.class);
        deletarRestauranteUseCase = mock(DeletarRestauranteUseCase.class);
        buscarRestaurantePorIdUseCase = mock(BuscarRestaurantePorIdUseCase.class);
        listarRestaurantesUseCase = mock(ListarRestaurantesUseCase.class);
        restauranteController = new RestauranteController(criarRestauranteUseCase,atualizarRestauranteUseCase,
                deletarRestauranteUseCase,buscarRestaurantePorIdUseCase, listarRestaurantesUseCase);

        restauranteResponseDTO = mock(RestauranteResponseDTO.class);

    }

    @Test
    public void deveCriarRestauranteComSucesso(){

        CreateRestauranteDTO createRestauranteDTO = mock(CreateRestauranteDTO.class);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("http://localhost");
        when(criarRestauranteUseCase.criar(createRestauranteDTO)).thenReturn(restauranteResponseDTO);

        ResponseEntity<RestauranteResponseDTO> response = restauranteController.criarRestaurante(createRestauranteDTO, uriBuilder);

        assertEquals(response.getBody(), restauranteResponseDTO);
        verify(criarRestauranteUseCase, times(1)).criar(createRestauranteDTO);
    }

    @Test
    public void deveListarRestauranteComSucesso(){
        Pageable pageable = PageRequest.of(0, 10);
        Page<RestauranteResponseDTO> responseDTOPage = new PageImpl<>(List.of(restauranteResponseDTO));
        when(listarRestaurantesUseCase.listar(pageable)).thenReturn(responseDTOPage);
        ResponseEntity<Page<RestauranteResponseDTO>> response = restauranteController.listarRestaurantes(pageable);

        assertNotNull(response);
        assertEquals(1, Objects.requireNonNull(response.getBody()).getTotalElements());
    }

    @Test
    public void deveBuscarRestaurantePeloID(){
        when(buscarRestaurantePorIdUseCase.buscarPorId(RESTAURANTE_ID)).thenReturn(restauranteResponseDTO);
        ResponseEntity<RestauranteResponseDTO> response = restauranteController.buscarRestaurantePorId(RESTAURANTE_ID);
        assertEquals(response.getBody(), restauranteResponseDTO);
        verify(buscarRestaurantePorIdUseCase, times(1)).buscarPorId(RESTAURANTE_ID);
    }

    @Test
    public void deveAtualizarRestaurante(){
        UpdateRestauranteDTO updateRestauranteDTO = mock(UpdateRestauranteDTO.class);
        Long restauranteID = 1L;

        when(atualizarRestauranteUseCase.atualizar(restauranteID, updateRestauranteDTO)).thenReturn(restauranteResponseDTO);
        ResponseEntity<RestauranteResponseDTO> response = restauranteController.atualizarRestaurante(restauranteID, updateRestauranteDTO);

        assertEquals(response.getBody(), restauranteResponseDTO);
        verify(atualizarRestauranteUseCase, times(1)).atualizar(restauranteID, updateRestauranteDTO);
    }

    @Test
    public void deveDeletarRestaurante(){
        doNothing().when(deletarRestauranteUseCase).deletar(RESTAURANTE_ID);
        restauranteController.deletarRestaurante(RESTAURANTE_ID);
        verify(deletarRestauranteUseCase,times(1)).deletar(RESTAURANTE_ID);
    }
}
