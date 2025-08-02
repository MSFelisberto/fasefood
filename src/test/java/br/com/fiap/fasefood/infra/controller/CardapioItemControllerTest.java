package br.com.fiap.fasefood.infra.controller;

import br.com.fiap.fasefood.core.usecase.interfaces.cardapio.AtualizarCardapioItemUseCase;
import br.com.fiap.fasefood.core.usecase.interfaces.cardapio.AtualizarCardapioItensBatchUseCase;
import br.com.fiap.fasefood.core.usecase.interfaces.cardapio.CriarCardapioItemUseCase;
import br.com.fiap.fasefood.core.usecase.interfaces.cardapio.CriarCardapioItemsBatchUseCase;
import br.com.fiap.fasefood.core.usecase.interfaces.cardapio.DeletarCardapioItemUseCase;
import br.com.fiap.fasefood.core.usecase.interfaces.cardapio.ListarCardapioItensUseCase;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CardapioItemResponseDTO;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CreateCardapioItemDTO;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CreateCardapioItemsBatchDTO;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.UpdateCardapioItemDTO;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.UpdateCardapioItemsBatchDTO;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CardapioItemControllerTest {

   private CardapioItemController cardapioItemController;

    private CriarCardapioItemUseCase criarUseCase;
    private ListarCardapioItensUseCase listarUseCase;
    private AtualizarCardapioItemUseCase atualizarUseCase;
    private DeletarCardapioItemUseCase deletarUseCase;
    private CriarCardapioItemsBatchUseCase criarEmLoteUseCase;
    private AtualizarCardapioItensBatchUseCase atualizarEmLoteUseCase;

    private CreateCardapioItemDTO createCardapioItemDTO;
    private CardapioItemResponseDTO cardapioItemResponseDTO;
    @BeforeEach
    public void setUp() {

        criarUseCase = mock(CriarCardapioItemUseCase.class);
        listarUseCase = mock(ListarCardapioItensUseCase.class);
        atualizarUseCase = mock(AtualizarCardapioItemUseCase.class);
        deletarUseCase = mock(DeletarCardapioItemUseCase.class);
        criarEmLoteUseCase = mock(CriarCardapioItemsBatchUseCase.class);
        atualizarEmLoteUseCase = mock(AtualizarCardapioItensBatchUseCase.class);
        cardapioItemController = new CardapioItemController(criarUseCase,listarUseCase, atualizarUseCase, deletarUseCase, criarEmLoteUseCase, atualizarEmLoteUseCase );

        createCardapioItemDTO = mock(CreateCardapioItemDTO.class);
        cardapioItemResponseDTO = mock(CardapioItemResponseDTO.class);
    }

    @Test
    public void deveCriarCardapioItemComSucesso(){
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("http://localhost");

        when(criarUseCase.criar(createCardapioItemDTO)).thenReturn(cardapioItemResponseDTO);
        cardapioItemController.criarItem(createCardapioItemDTO,uriBuilder);

        verify(criarUseCase, times(1)).criar(createCardapioItemDTO);
    }

    @Test
    public void deveCriarItensEmLoteComSucesso(){
        CreateCardapioItemsBatchDTO createCardapioItemsBatchDTO = mock(CreateCardapioItemsBatchDTO.class);
        CardapioItemResponseDTO cardapioItem = mock(CardapioItemResponseDTO.class);
        List<CardapioItemResponseDTO> cardapioItemResponseDTOS = List.of(cardapioItem);

        when(criarEmLoteUseCase.criarEmLote(createCardapioItemsBatchDTO)).thenReturn(cardapioItemResponseDTOS);
        ResponseEntity<List<CardapioItemResponseDTO>> response = cardapioItemController.criarItensEmLote(createCardapioItemsBatchDTO);

        assertEquals(response.getBody(), cardapioItemResponseDTOS);
        verify(criarEmLoteUseCase, times(1)).criarEmLote(createCardapioItemsBatchDTO);
    }

    @Test
    public void deveListarOsItensDoCardapioComSucesso(){
        Long cardapioID = 1L;
        Pageable pageable = PageRequest.of(0, 10);
        Page<CardapioItemResponseDTO> page = new PageImpl<>(List.of(cardapioItemResponseDTO));

        when(listarUseCase.listar(cardapioID, pageable)).thenReturn(page);
        ResponseEntity<Page<CardapioItemResponseDTO>> response = cardapioItemController.listarItens(cardapioID, pageable);

        assertNotNull(response);
        assertEquals(1, Objects.requireNonNull(response.getBody()).getTotalElements());

    }

    @Test
    public void deveAtualizarItemCardapio(){
        Long cardapioItemID = 1L;
        UpdateCardapioItemDTO updateCardapioItemDTO = mock(UpdateCardapioItemDTO.class);

        when(atualizarUseCase.atualizar(cardapioItemID, updateCardapioItemDTO)).thenReturn(cardapioItemResponseDTO);
        ResponseEntity<CardapioItemResponseDTO>  response = cardapioItemController.atualizarItem(cardapioItemID, updateCardapioItemDTO);

        verify(atualizarUseCase, times(1)).atualizar(cardapioItemID, updateCardapioItemDTO);
        assertEquals(response.getBody(), cardapioItemResponseDTO);

    }

    @Test
    public void deveAtualizarItensEmLote(){
        UpdateCardapioItemsBatchDTO updateCardapioItemsBatchDTO = mock(UpdateCardapioItemsBatchDTO.class);
        when(atualizarEmLoteUseCase.atualizarEmLote(updateCardapioItemsBatchDTO)).thenReturn(List.of(cardapioItemResponseDTO));
        ResponseEntity<List<CardapioItemResponseDTO>> response = cardapioItemController.atualizarItensEmLote(updateCardapioItemsBatchDTO);

        verify(atualizarEmLoteUseCase, times(1)).atualizarEmLote(updateCardapioItemsBatchDTO);
        assertNotNull(response);
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
    }

    @Test
    public void deveDeletarItemCardapioComSucesso(){
        doNothing().when(deletarUseCase).deletar(anyLong());
        cardapioItemController.deletarItem(anyLong());
        verify(deletarUseCase,times(1)).deletar(anyLong());
    }
}
