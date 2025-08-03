package br.com.fiap.fasefood.infra.controller;

import br.com.fiap.fasefood.application.usecases.restaurante.RestauranteOutput;
import br.com.fiap.fasefood.application.usecases.restaurante.atualizar.AtualizarRestauranteUseCase;
import br.com.fiap.fasefood.application.usecases.restaurante.atualizar.UpdateRestauranteInput;
import br.com.fiap.fasefood.application.usecases.restaurante.criar.CriarRestauranteInput;
import br.com.fiap.fasefood.application.usecases.restaurante.criar.CriarRestauranteUseCase;
import br.com.fiap.fasefood.application.usecases.restaurante.deletar.DeletarRestauranteUseCase;
import br.com.fiap.fasefood.application.usecases.restaurante.listar.BuscarRestaurantePorIdUseCase;
import br.com.fiap.fasefood.application.usecases.restaurante.listar.ListarRestaurantesUseCase;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PageOutput;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PaginationInput;
import br.com.fiap.fasefood.infra.controllers.RestauranteController;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CardapioResponseDTO;
import br.com.fiap.fasefood.infra.controllers.dto.restaurante.CreateRestauranteDTO;
import br.com.fiap.fasefood.infra.controllers.dto.restaurante.RestauranteResponseDTO;
import br.com.fiap.fasefood.infra.controllers.dto.restaurante.UpdateRestauranteDTO;
import br.com.fiap.fasefood.infra.controllers.mapper.restaurante.RestauranteMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
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
import static org.mockito.Mockito.mockStatic;
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
    }

    @Test
    public void deveCriarRestauranteComSucesso(){
        try(MockedStatic<RestauranteMapper> mapperMock = mockStatic(RestauranteMapper.class)) {

            CreateRestauranteDTO createRestauranteDTO = mock(CreateRestauranteDTO.class);
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("http://localhost");
            CriarRestauranteInput criarRestauranteInput = mock(CriarRestauranteInput.class);
            RestauranteOutput restauranteOutput = mock(RestauranteOutput.class);
            RestauranteResponseDTO restauranteResponseDTO = mock(RestauranteResponseDTO.class);

            mapperMock.when(()-> RestauranteMapper.toCriarRestauranteInput(createRestauranteDTO)).thenReturn(criarRestauranteInput);
            when(criarRestauranteUseCase.criar(criarRestauranteInput)).thenReturn(restauranteOutput);
            mapperMock.when(()-> RestauranteMapper.toResponseDTO(restauranteOutput)).thenReturn(restauranteResponseDTO);

            ResponseEntity<RestauranteResponseDTO> response = restauranteController.criarRestaurante(createRestauranteDTO, uriBuilder);

            assertEquals(response.getBody(), restauranteResponseDTO);
            verify(criarRestauranteUseCase, times(1)).criar(criarRestauranteInput);
        }
    }

    @Test
    public void deveListarRestauranteComSucesso(){
        try(MockedStatic<RestauranteMapper> mapperMock = mockStatic(RestauranteMapper.class)) {

            Pageable pageable = PageRequest.of(0, 10);
            PaginationInput paginationInput = new PaginationInput(0, 10, "nome", "asc");
            RestauranteOutput restauranteOutput = mock(RestauranteOutput.class);
            PageOutput<RestauranteOutput> pageOutput =  new PageOutput<>(List.of(restauranteOutput), 0, 10, 1 , 1L);
            CardapioResponseDTO cardapioResponseDTO = new CardapioResponseDTO(1L, 1L, "Nome", "Descrição");
            Page<CardapioResponseDTO> responseDTOPage = new PageImpl<>(List.of(cardapioResponseDTO));

            mapperMock.when(()-> RestauranteMapper.toPaginationInput(pageable)).thenReturn(paginationInput);
            when(listarRestaurantesUseCase.listar(paginationInput)).thenReturn(pageOutput);
            mapperMock.when(()-> RestauranteMapper.toRestauranteDtoPaginacao(pageOutput)).thenReturn(responseDTOPage);

            ResponseEntity<Page<RestauranteResponseDTO>> result = restauranteController.listarRestaurantes(pageable);

            assertNotNull(result);
            assertEquals(1, Objects.requireNonNull(result.getBody()).getTotalElements());
        }
    }
    @Test
    public void deveBuscarRestaurantePeloID(){
        try(MockedStatic<RestauranteMapper> mapperMock = mockStatic(RestauranteMapper.class)) {
            RestauranteOutput restauranteOutput = mock(RestauranteOutput.class);
            RestauranteResponseDTO restauranteResponseDTO = mock(RestauranteResponseDTO.class);

            when(buscarRestaurantePorIdUseCase.buscarPorId(RESTAURANTE_ID)).thenReturn(restauranteOutput);
            mapperMock.when(()-> RestauranteMapper.toResponseDTO(restauranteOutput)).thenReturn(restauranteResponseDTO);

            ResponseEntity<RestauranteResponseDTO> response = restauranteController.buscarRestaurantePorId(RESTAURANTE_ID);
            assertEquals(response.getBody(), restauranteResponseDTO);
            verify(buscarRestaurantePorIdUseCase, times(1)).buscarPorId(RESTAURANTE_ID);
        }
    }

    @Test
    public void deveAtualizarRestaurante(){
        try(MockedStatic<RestauranteMapper> mapperMock = mockStatic(RestauranteMapper.class)) {

            UpdateRestauranteDTO updateRestauranteDTO = mock(UpdateRestauranteDTO.class);
            Long restauranteID = 1L;
            UpdateRestauranteInput updateRestauranteInput = mock(UpdateRestauranteInput.class);
            RestauranteOutput restauranteOutput = mock(RestauranteOutput.class);
            RestauranteResponseDTO restauranteResponseDTO = mock(RestauranteResponseDTO.class);

            mapperMock.when(()-> RestauranteMapper.toUpdateRestauranteInput(updateRestauranteDTO)).thenReturn(updateRestauranteInput);
            when(atualizarRestauranteUseCase.atualizar(restauranteID, updateRestauranteInput)).thenReturn(restauranteOutput);
            mapperMock.when(()-> RestauranteMapper.toResponseDTO(restauranteOutput)).thenReturn(restauranteResponseDTO);

            ResponseEntity<RestauranteResponseDTO> response = restauranteController.atualizarRestaurante(restauranteID, updateRestauranteDTO);

            assertEquals(response.getBody(), restauranteResponseDTO);
            verify(atualizarRestauranteUseCase, times(1)).atualizar(restauranteID, updateRestauranteInput);
        }
    }

    @Test
    public void deveDeletarRestaurante(){
        doNothing().when(deletarRestauranteUseCase).deletar(RESTAURANTE_ID);
        restauranteController.deletarRestaurante(RESTAURANTE_ID);
        verify(deletarRestauranteUseCase,times(1)).deletar(RESTAURANTE_ID);
    }
}