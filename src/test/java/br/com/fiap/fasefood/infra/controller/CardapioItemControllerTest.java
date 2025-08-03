package br.com.fiap.fasefood.infra.controller;

import br.com.fiap.fasefood.application.usecases.cardapio.atualizar.AtualizarCardapioItemBatchInput;
import br.com.fiap.fasefood.application.usecases.cardapio.atualizar.AtualizarCardapioItemInput;
import br.com.fiap.fasefood.application.usecases.cardapio.atualizar.AtualizarCardapioItemUseCase;
import br.com.fiap.fasefood.application.usecases.cardapio.atualizar.AtualizarCardapioItensBatchUseCase;
import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioItemInput;
import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioItemOutput;
import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioItemUseCase;
import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioItemsBatchInput;
import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioItemsBatchUseCase;
import br.com.fiap.fasefood.application.usecases.cardapio.deletar.RemoverCardapioItemUseCase;
import br.com.fiap.fasefood.application.usecases.cardapio.deletar.RemoverItensCardapioUseCase;
import br.com.fiap.fasefood.application.usecases.cardapio.listar.ListarCardapioItensUseCase;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PageOutput;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PaginationInput;
import br.com.fiap.fasefood.infra.controllers.CardapioItemController;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CardapioItemResponseDTO;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CreateCardapioItemDTO;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CreateCardapioItemsBatchDTO;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.RemoverItensCardapioDTO;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.UpdateCardapioItemDTO;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.UpdateCardapioItemRequestDTO;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.UpdateCardapioItemsBatchDTO;
import br.com.fiap.fasefood.infra.controllers.mapper.cardapio.CardapioItemMapper;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CardapioItemControllerTest {

    private CardapioItemController cardapioItemController;

    private CriarCardapioItemUseCase criarUseCase;
    private ListarCardapioItensUseCase listarUseCase;
    private AtualizarCardapioItemUseCase atualizarUseCase;
    private RemoverCardapioItemUseCase removerUseCase;
    private RemoverItensCardapioUseCase removerItensUseCase;
    private CriarCardapioItemsBatchUseCase criarEmLoteUseCase;
    private AtualizarCardapioItensBatchUseCase atualizarEmLoteUseCase;

    private CreateCardapioItemDTO createCardapioItemDTO;
    private CriarCardapioItemInput criarCardapioItemInput;
    private CriarCardapioItemOutput criarCardapioItemOutput;

    @BeforeEach
    public void setUp() {

        criarUseCase = mock(CriarCardapioItemUseCase.class);
        listarUseCase = mock(ListarCardapioItensUseCase.class);
        atualizarUseCase = mock(AtualizarCardapioItemUseCase.class);
        removerUseCase = mock(RemoverCardapioItemUseCase.class);
        removerItensUseCase = mock(RemoverItensCardapioUseCase.class);
        criarEmLoteUseCase = mock(CriarCardapioItemsBatchUseCase.class);
        atualizarEmLoteUseCase = mock(AtualizarCardapioItensBatchUseCase.class);
        cardapioItemController = new CardapioItemController(criarUseCase, listarUseCase, atualizarUseCase, removerUseCase, removerItensUseCase, criarEmLoteUseCase, atualizarEmLoteUseCase);

        createCardapioItemDTO = mock(CreateCardapioItemDTO.class);
        criarCardapioItemInput = mock(CriarCardapioItemInput.class);
        criarCardapioItemOutput = mock(CriarCardapioItemOutput.class);
    }

    @Test
    public void deveCriarCardapioItemComSucesso() {
        try (MockedStatic<CardapioItemMapper> cardapioItemMapper = mockStatic(CardapioItemMapper.class)) {
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("http://localhost");
            CardapioItemResponseDTO cardapioItemResponseDTO = new CardapioItemResponseDTO(1L, 1L, "Nome", "Descrição", BigDecimal.valueOf(10), true, "Path");

            cardapioItemMapper.when(() -> CardapioItemMapper.toCriarCardapioItemInput(createCardapioItemDTO)).thenReturn(criarCardapioItemInput);
            when(criarUseCase.criar(criarCardapioItemInput)).thenReturn(criarCardapioItemOutput);
            cardapioItemMapper.when(() -> CardapioItemMapper.toCardapioItemResponseDTO(criarCardapioItemOutput)).thenReturn(cardapioItemResponseDTO);

            ResponseEntity<CardapioItemResponseDTO> response = cardapioItemController.criarItem(createCardapioItemDTO, uriBuilder);

            assertNotNull(response);
            verify(criarUseCase, times(1)).criar(criarCardapioItemInput);
            assertEquals(cardapioItemResponseDTO, response.getBody());
        }
    }

    @Test
    public void deveCriarItensEmLoteComSucesso(){
        try (MockedStatic<CardapioItemMapper> cardapioItemMapper = mockStatic(CardapioItemMapper.class)) {

            CreateCardapioItemsBatchDTO createCardapioItemsBatchDTO = mock(CreateCardapioItemsBatchDTO.class);
            CriarCardapioItemOutput cardapioItem = mock(CriarCardapioItemOutput.class);
            List<CriarCardapioItemOutput> cardapioItemResponseDTOS = List.of(cardapioItem);
            CriarCardapioItemsBatchInput criarCardapioItemsBatchInput = mock(CriarCardapioItemsBatchInput.class);
            CardapioItemResponseDTO cardapioItemResponse = new CardapioItemResponseDTO(1L, 1L, "Nome", "Descrição", BigDecimal.valueOf(10), true, "Path");
            List<CardapioItemResponseDTO> itemResponseDTOS = List.of(cardapioItemResponse);

            cardapioItemMapper.when(()-> CardapioItemMapper.toCriarCardapioItemsBatchInput(createCardapioItemsBatchDTO)).thenReturn(criarCardapioItemsBatchInput);
            when(criarEmLoteUseCase.criarEmLote(criarCardapioItemsBatchInput)).thenReturn(cardapioItemResponseDTOS);
            cardapioItemMapper.when(()-> CardapioItemMapper.toResponseDTOList(cardapioItemResponseDTOS)).thenReturn(itemResponseDTOS);

            ResponseEntity<List<CardapioItemResponseDTO>> response = cardapioItemController.criarItensEmLote(createCardapioItemsBatchDTO);

            assertEquals(Objects.requireNonNull(response.getBody()).getFirst(), cardapioItemResponse);
            verify(criarEmLoteUseCase, times(1)).criarEmLote(criarCardapioItemsBatchInput);
        }
    }

    @Test
    public void deveListarOsItensDoCardapioComSucesso(){
        try (MockedStatic<CardapioItemMapper> cardapioItemMapper = mockStatic(CardapioItemMapper.class)) {

            Long cardapioID = 1L;
            Pageable pageable = PageRequest.of(0, 10);
            PaginationInput paginationInput = new PaginationInput(0, 10, "nome", "asc");
            CriarCardapioItemOutput criarCardapioItemOutPut = mock(CriarCardapioItemOutput.class);
            PageOutput<CriarCardapioItemOutput> pageOutput =  new PageOutput<>(List.of(criarCardapioItemOutPut), 0, 10, 1 , 1L);
            CardapioItemResponseDTO cardapioResponseOutput = mock(CardapioItemResponseDTO.class);
            Page<CardapioItemResponseDTO> responseDTOPage = new PageImpl<>(List.of(cardapioResponseOutput));

            cardapioItemMapper.when(()-> CardapioItemMapper.toPaginationInput(pageable)).thenReturn(paginationInput);
            when(listarUseCase.listar(cardapioID, paginationInput)).thenReturn(pageOutput);
            cardapioItemMapper.when(()-> CardapioItemMapper.toCardapioItemResponseDTOPage(pageOutput)).thenReturn(responseDTOPage);

            ResponseEntity<Page<CardapioItemResponseDTO>> response = cardapioItemController.listarItens(cardapioID, pageable);

            assertNotNull(response);
            assertEquals(1, Objects.requireNonNull(response.getBody()).getTotalElements());
        }

    }

    @Test
    public void deveAtualizarItemCardapio(){
        try (MockedStatic<CardapioItemMapper> cardapioItemMapper = mockStatic(CardapioItemMapper.class)) {

            Long cardapioItemID = 1L;
            UpdateCardapioItemDTO updateCardapioItemRequestDTO = new UpdateCardapioItemDTO("Nome", "Descrição", BigDecimal.valueOf(10), true,"Path");
            AtualizarCardapioItemInput atualizarCardapioItemInput = mock(AtualizarCardapioItemInput.class);
            CriarCardapioItemOutput criarCardapioItemOutput = mock(CriarCardapioItemOutput.class);
            CardapioItemResponseDTO cardapioItemResponseDTO = mock(CardapioItemResponseDTO.class);

            cardapioItemMapper.when(()-> CardapioItemMapper.toAtualizarCardapioItemInput(updateCardapioItemRequestDTO)).thenReturn(atualizarCardapioItemInput);
            when(atualizarUseCase.atualizar(cardapioItemID, atualizarCardapioItemInput)).thenReturn(criarCardapioItemOutput);
            cardapioItemMapper.when(()-> CardapioItemMapper.toCardapioItemResponseDTO(criarCardapioItemOutput)).thenReturn(cardapioItemResponseDTO);

            ResponseEntity<CardapioItemResponseDTO> response = cardapioItemController.atualizarItem(cardapioItemID, updateCardapioItemRequestDTO);

            verify(atualizarUseCase, times(1)).atualizar(cardapioItemID, atualizarCardapioItemInput);
            assertEquals(response.getBody(), cardapioItemResponseDTO);
        }

    }

    @Test
    public void deveAtualizarItensEmLote(){
        try (MockedStatic<CardapioItemMapper> cardapioItemMapper = mockStatic(CardapioItemMapper.class)) {

            UpdateCardapioItemRequestDTO updateCardapioItemRequestDTO = new UpdateCardapioItemRequestDTO(1L,"Nome", "Descrição", BigDecimal.valueOf(10), true,"Path");
            UpdateCardapioItemsBatchDTO updateCardapioItemsBatchDTO = new UpdateCardapioItemsBatchDTO(List.of(updateCardapioItemRequestDTO));
            List<AtualizarCardapioItemBatchInput> inputList = List.of(mock(AtualizarCardapioItemBatchInput.class));
            List<CriarCardapioItemOutput> outputList = List.of(mock(CriarCardapioItemOutput.class));
            List<CardapioItemResponseDTO>responseDTOS = List.of(mock(CardapioItemResponseDTO.class));

            cardapioItemMapper.when(()-> CardapioItemMapper.toAtualizarCardapioItemBatchInputList(updateCardapioItemsBatchDTO)).thenReturn(inputList);
            when(atualizarEmLoteUseCase.atualizarEmLote(inputList)).thenReturn(outputList);
            cardapioItemMapper.when(()-> CardapioItemMapper.toResponseDTOList(outputList)).thenReturn(responseDTOS);


            ResponseEntity<List<CardapioItemResponseDTO>> response = cardapioItemController.atualizarItensEmLote(updateCardapioItemsBatchDTO);

            verify(atualizarEmLoteUseCase, times(1)).atualizarEmLote(inputList);
            assertNotNull(response);
            assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        }
    }

    @Test
    public void deveRemoverItemCardapioComSucesso(){
        doNothing().when(removerUseCase).remover(anyLong());
        cardapioItemController.removerItem(anyLong());
        verify(removerUseCase,times(1)).remover(anyLong());
    }

    @Test
    public void deveRemoverItensCardapioComSucesso(){
        RemoverItensCardapioDTO removerItensCardapioDTO = new RemoverItensCardapioDTO(List.of(1L));
        doNothing().when(removerItensUseCase).removerEmLote(removerItensCardapioDTO.itemIds());
        cardapioItemController.removerItens(removerItensCardapioDTO);
        verify(removerItensUseCase,times(1)).removerEmLote(removerItensCardapioDTO.itemIds());
    }
}