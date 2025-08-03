package br.com.fiap.fasefood.application.usecases.cardapio.atualizar;

import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioItemOutput;
import br.com.fiap.fasefood.application.usecases.cardapio.mappers.CardapioOutputMapper;
import br.com.fiap.fasefood.core.entities.CardapioItem;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.gateways.CardapioItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
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

    private AtualizarCardapioItemInput atualizarCardapioItemInput;
    private CardapioItem cardapioItem;

    private static final Long CARDAPIO_ITEM_ID = 1L;

    @BeforeEach
    public void setUp() {
        cardapioItemRepository = mock(CardapioItemRepository.class);
        atualizarCardapioItemUseCaseTest = new AtualizarCardapioItemUseCaseImpl(cardapioItemRepository);

        atualizarCardapioItemInput = new AtualizarCardapioItemInput("teste", "desc", BigDecimal.valueOf(10.0), true, "path");
        cardapioItem = mock(CardapioItem.class);
    }

    @Test
    public void deveAtualizarComSucesso() {
      try(MockedStatic<CardapioOutputMapper> cardapioOutPutMapper = mockStatic(CardapioOutputMapper.class)) {

            CardapioItem cardapioItemAtualizado = mock(CardapioItem.class);
            CriarCardapioItemOutput criarCardapioItemOutput = mock(CriarCardapioItemOutput.class);
            when(cardapioItemRepository.findById(CARDAPIO_ITEM_ID)).thenReturn(Optional.of(cardapioItem));
            when(cardapioItemRepository.salvar(cardapioItem)).thenReturn(cardapioItemAtualizado);
          cardapioOutPutMapper.when(() -> CardapioOutputMapper.toCriarCardapioItemOutput(cardapioItemAtualizado)).thenReturn(criarCardapioItemOutput);

          CriarCardapioItemOutput response = atualizarCardapioItemUseCaseTest.atualizar(CARDAPIO_ITEM_ID, atualizarCardapioItemInput);

            verify(cardapioItemRepository, times(1)).salvar(any());
            assertEquals(response, criarCardapioItemOutput);
        }
    }

    @Test
    public void deveLancarExcecaoQuandoCardapioNaoEncontrado() {

        when(cardapioItemRepository.findById(CARDAPIO_ITEM_ID)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> atualizarCardapioItemUseCaseTest.atualizar(1L, atualizarCardapioItemInput));

        assertEquals("Item de cardápio com ID: 1 não encontrado.", exception.getMessage());
    }
}
