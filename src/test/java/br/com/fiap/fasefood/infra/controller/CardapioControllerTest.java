package br.com.fiap.fasefood.infra.controller;

import br.com.fiap.fasefood.core.usecase.interfaces.cardapio.CriarCardapioUseCase;
import br.com.fiap.fasefood.core.usecase.interfaces.cardapio.ListarCardapiosUseCase;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CardapioResponseDTO;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CreateCardapioDTO;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CardapioControllerTest {


    private CardapioController cardapioController;
    private CriarCardapioUseCase criarUseCase;
    private ListarCardapiosUseCase listarUseCase;

   @BeforeEach
    public void setUp() {

       criarUseCase = mock(CriarCardapioUseCase.class);
       listarUseCase = mock(ListarCardapiosUseCase.class);
       cardapioController = new CardapioController(criarUseCase,listarUseCase);
    }

    @Test
    public void devoCriarCardapioComSucesso(){
        CreateCardapioDTO createCardapioDTO = mock(CreateCardapioDTO.class);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("http://localhost");
        CardapioResponseDTO cardapioResponseDTO = mock(CardapioResponseDTO.class);

        when(criarUseCase.criar(createCardapioDTO)).thenReturn(cardapioResponseDTO);
        ResponseEntity<CardapioResponseDTO> response = cardapioController.criarCardapio(createCardapioDTO, uriBuilder);

        assertEquals(response.getBody(), cardapioResponseDTO);
        verify(criarUseCase, times(1)).criar(createCardapioDTO);

    }

    @Test
    public void devoListarOsCardapiosComSucesso(){
       Long restauranteID = 1L;
       Pageable pageable = PageRequest.of(0, 10);
       CardapioResponseDTO cardapioResponseDTO = mock(CardapioResponseDTO.class);
       Page<CardapioResponseDTO> page = new PageImpl<>(List.of(cardapioResponseDTO));
       when(listarUseCase.listar(restauranteID, pageable)).thenReturn(page);
       ResponseEntity<Page<CardapioResponseDTO>> result =  cardapioController.listarCardapios(restauranteID, pageable);

        assertNotNull(result);
        assertEquals(1, Objects.requireNonNull(result.getBody()).getTotalElements());
    }

}
