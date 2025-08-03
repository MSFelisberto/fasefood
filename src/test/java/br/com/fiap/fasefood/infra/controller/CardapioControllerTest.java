package br.com.fiap.fasefood.infra.controller;

import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioInput;
import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioOutput;
import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioUseCase;
import br.com.fiap.fasefood.application.usecases.cardapio.listar.CardapioResponseOutput;
import br.com.fiap.fasefood.application.usecases.cardapio.listar.ListarCardapiosUseCase;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PageOutput;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PaginationInput;
import br.com.fiap.fasefood.infra.controllers.CardapioController;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CardapioResponseDTO;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CreateCardapioDTO;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CreateCardapioResponseDTO;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.ItensCreateCardapioItemDTO;
import br.com.fiap.fasefood.infra.controllers.mapper.cardapio.CardapioMapper;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CardapioControllerTest {

    private CardapioController cardapioController;
    private CriarCardapioUseCase criarUseCase;
    private ListarCardapiosUseCase listarUseCase;
    private CreateCardapioDTO createCardapioDTO;
    private CriarCardapioInput criarCardapioInput;

    @BeforeEach
    public void setUp() {

        criarUseCase = mock(CriarCardapioUseCase.class);
        listarUseCase = mock(ListarCardapiosUseCase.class);
        cardapioController = new CardapioController(criarUseCase,listarUseCase);

        ItensCreateCardapioItemDTO itensCreateCardapioItemDTO = new ItensCreateCardapioItemDTO("Nome", "Descrição", BigDecimal.valueOf(10), true, "Path");

        createCardapioDTO = new CreateCardapioDTO(1L, "Nome", "Descrição",List.of(itensCreateCardapioItemDTO));
        criarCardapioInput = mock(CriarCardapioInput.class);
    }

    @Test
    public void devoCriarCardapioComSucesso(){
        try(MockedStatic<CardapioMapper> cardapioMapper = mockStatic(CardapioMapper.class)) {

            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("http://localhost");

            CriarCardapioOutput criarCardapioOutput = new CriarCardapioOutput(1L);
            CreateCardapioResponseDTO createCardapioResponseDTO = new CreateCardapioResponseDTO(1L);

            cardapioMapper.when(()-> CardapioMapper.toCriarCardapioInput(createCardapioDTO)).thenReturn(criarCardapioInput);
            when(criarUseCase.criar(criarCardapioInput)).thenReturn(criarCardapioOutput);
            cardapioMapper.when(()-> CardapioMapper.toResponseCriarCardapioDTO(criarCardapioOutput)).thenReturn(createCardapioResponseDTO);

            ResponseEntity<CreateCardapioResponseDTO> response = cardapioController.criarCardapio(createCardapioDTO, uriBuilder);

            assertEquals(response.getBody(), createCardapioResponseDTO);
            verify(criarUseCase, times(1)).criar(criarCardapioInput);
        }
    }

    @Test
    public void devoListarOsCardapiosComSucesso(){
        try(MockedStatic<CardapioMapper> cardapioMapper = mockStatic(CardapioMapper.class)) {

            Long restauranteID = 1L;
            Pageable pageable = PageRequest.of(0, 10);
            PaginationInput paginationInput = new PaginationInput(0, 10, "nome", "asc");
            CardapioResponseOutput cardapioResponseOutput = new CardapioResponseOutput(1L, 1L, "Nome", "Descrição");
            PageOutput<CardapioResponseOutput> pageOutput =  new PageOutput<>(List.of(cardapioResponseOutput), 0, 10, 1 , 1L);
            CardapioResponseDTO cardapioResponseDTO = new CardapioResponseDTO(1L, 1L, "Nome", "Descrição");
            Page<CardapioResponseDTO> responseDTOPage = new PageImpl<>(List.of(cardapioResponseDTO));

            cardapioMapper.when(()-> CardapioMapper.toPaginationInput(pageable)).thenReturn(paginationInput);
            when(listarUseCase.listar(restauranteID, paginationInput)).thenReturn(pageOutput);
            cardapioMapper.when(()-> CardapioMapper.toRestauranteDtoPaginacao(pageOutput)).thenReturn(responseDTOPage);

            ResponseEntity<Page<CardapioResponseDTO>> result = cardapioController.listarCardapios(restauranteID, pageable);

            assertNotNull(result);
            assertEquals(1, Objects.requireNonNull(result.getBody()).getTotalElements());
        }
    }
}
