package br.com.fiap.fasefood.application.usecace.cardapio;

import br.com.fiap.fasefood.application.usecase.cardapio.AtualizarCardapioItensBatchUseCaseImpl;
import br.com.fiap.fasefood.core.domain.entities.CardapioItem;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioItemRepository;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CardapioItemResponseDTO;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.UpdateCardapioItemRequestDTO;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.UpdateCardapioItemsBatchDTO;
import br.com.fiap.fasefood.infra.controller.mapper.cardapio.CardapioItemMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class AtualizarCardapioItensBatchUseCaseImplTest {

    private AtualizarCardapioItensBatchUseCaseImpl atualizarCardapioItensBatchUseCaseTest;
    private CardapioItemRepository cardapioItemRepository;
    private UpdateCardapioItemsBatchDTO updateCardapioItemDTO;
    private CardapioItem cardapioItem;
    private UpdateCardapioItemRequestDTO updateCardapioItemRequestDTO;

    @BeforeEach
    public void setUp() {
        cardapioItemRepository = Mockito.mock(CardapioItemRepository.class);
        atualizarCardapioItensBatchUseCaseTest = new AtualizarCardapioItensBatchUseCaseImpl(cardapioItemRepository);

        updateCardapioItemDTO = mock(UpdateCardapioItemsBatchDTO.class);
        cardapioItem = mock(CardapioItem.class);
        updateCardapioItemRequestDTO = mock(UpdateCardapioItemRequestDTO.class);
    }


    @Test
    public void deveAtualizarEmLoteComSucesso() {
        try(MockedStatic<CardapioItemMapper> cardapioItemMapper = mockStatic(CardapioItemMapper.class)) {
            CardapioItem cardapioItemSalvo = mock(CardapioItem.class);
            CardapioItemResponseDTO responseDTO = mock(CardapioItemResponseDTO.class);

            when(updateCardapioItemDTO.itens()).thenReturn(Collections.singletonList(updateCardapioItemRequestDTO));
            when(cardapioItemRepository.findById(updateCardapioItemRequestDTO.id())).thenReturn(Optional.of(cardapioItem));
            when(cardapioItemRepository.salvar(cardapioItem)).thenReturn(cardapioItemSalvo);
            cardapioItemMapper.when(() -> CardapioItemMapper.toResponseDTO(cardapioItemSalvo)).thenReturn(responseDTO);

            List<CardapioItemResponseDTO> response = atualizarCardapioItensBatchUseCaseTest.atualizarEmLote(updateCardapioItemDTO);

            verify(cardapioItemRepository, times(1)).salvar(cardapioItem);
            assertEquals(1, response.size());
            assertEquals(responseDTO, response.getFirst());
        }
    }

    @Test
    public void deveLancarExcecaoQuandoCardapioNaoEncontrado() {
        when(updateCardapioItemDTO.itens()).thenReturn(Collections.singletonList(updateCardapioItemRequestDTO));
        when(cardapioItemRepository.findById(updateCardapioItemRequestDTO.id())).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> atualizarCardapioItensBatchUseCaseTest.atualizarEmLote(updateCardapioItemDTO));

        assertEquals("Item de cardápio com ID: 0 não encontrado.", exception.getMessage());
    }
}
