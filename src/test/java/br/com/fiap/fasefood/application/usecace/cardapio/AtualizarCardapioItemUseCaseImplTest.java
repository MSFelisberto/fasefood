package br.com.fiap.fasefood.application.usecace.cardapio;

import br.com.fiap.fasefood.application.usecase.cardapio.AtualizarCardapioItemUseCaseImpl;
import br.com.fiap.fasefood.core.domain.entities.CardapioItem;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioItemRepository;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CardapioItemResponseDTO;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.UpdateCardapioItemDTO;
import br.com.fiap.fasefood.infra.controller.mapper.cardapio.CardapioItemMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AtualizarCardapioItemUseCaseImplTest {


    private AtualizarCardapioItemUseCaseImpl atualizarCardapioItemUseCaseTest;
    private CardapioItemRepository cardapioItemRepository;

    private UpdateCardapioItemDTO updateCardapioItemDTO;
    private CardapioItem cardapioItem;

    private static final Long CARDAPIO_ITEM_ID = 1L;

    @BeforeEach
    public void setUp() {
        cardapioItemRepository = mock(CardapioItemRepository.class);
        atualizarCardapioItemUseCaseTest = new AtualizarCardapioItemUseCaseImpl(cardapioItemRepository);

        updateCardapioItemDTO = mock(UpdateCardapioItemDTO.class);
        cardapioItem = mock(CardapioItem.class);
    }

    @Test
    public void deveAtualizarComSucesso() {
      try(MockedStatic<CardapioItemMapper> cardapioItemMapper = mockStatic(CardapioItemMapper.class)) {

            CardapioItem cardapioItemAtualizado = mock(CardapioItem.class);
            CardapioItemResponseDTO cardapioItemResponseDTO = mock(CardapioItemResponseDTO.class);
            when(cardapioItemRepository.findById(CARDAPIO_ITEM_ID)).thenReturn(Optional.of(cardapioItem));
            when(cardapioItemRepository.salvar(cardapioItem)).thenReturn(cardapioItemAtualizado);
            cardapioItemMapper.when(() -> CardapioItemMapper.toResponseDTO(cardapioItemAtualizado)).thenReturn(cardapioItemResponseDTO);

            CardapioItemResponseDTO response = atualizarCardapioItemUseCaseTest.atualizar(CARDAPIO_ITEM_ID, updateCardapioItemDTO);

            verify(cardapioItemRepository, times(1)).salvar(any());
            assertEquals(response, cardapioItemResponseDTO);
        }
    }

    @Test
    public void deveLancarExcecaoQuandoCardapioNaoEncontrado() {

        when(cardapioItemRepository.findById(CARDAPIO_ITEM_ID)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> atualizarCardapioItemUseCaseTest.atualizar(1L, updateCardapioItemDTO));

        assertEquals("Item de cardápio com ID: 1 não encontrado.", exception.getMessage());
    }
}
