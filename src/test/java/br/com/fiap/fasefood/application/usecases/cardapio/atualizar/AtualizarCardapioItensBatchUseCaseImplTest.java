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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
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

    private AtualizarCardapioItemBatchInput atualizarCardapioItemBatchInput;
    private CardapioItem cardapioItem;

    @BeforeEach
    public void setUp() {
        cardapioItemRepository = Mockito.mock(CardapioItemRepository.class);
        atualizarCardapioItensBatchUseCaseTest = new AtualizarCardapioItensBatchUseCaseImpl(cardapioItemRepository);

        atualizarCardapioItemBatchInput = new AtualizarCardapioItemBatchInput(1L, "teste", "desc", BigDecimal.valueOf(10.0),true, "path");
        cardapioItem = mock(CardapioItem.class);
    }

    @Test
    public void deveAtualizarEmLoteComSucesso() {
        try(MockedStatic<CardapioOutputMapper> cardapioOutputMapper = mockStatic(CardapioOutputMapper.class)) {
            CardapioItem cardapioItemSalvo = mock(CardapioItem.class);
            CriarCardapioItemOutput cardapioItemOutput = mock(CriarCardapioItemOutput.class);

            when(cardapioItemRepository.findById(atualizarCardapioItemBatchInput.id())).thenReturn(Optional.of(cardapioItem));
            when(cardapioItemRepository.salvar(cardapioItem)).thenReturn(cardapioItemSalvo);
            cardapioOutputMapper.when(() -> CardapioOutputMapper.toCriarCardapioItemOutput(cardapioItemSalvo)).thenReturn(cardapioItemOutput);

            List<CriarCardapioItemOutput> response = atualizarCardapioItensBatchUseCaseTest.atualizarEmLote(List.of(atualizarCardapioItemBatchInput));

            verify(cardapioItemRepository, times(1)).salvar(cardapioItem);
            assertEquals(1, response.size());
            assertEquals(cardapioItemOutput, response.getFirst());
        }
    }

    @Test
    public void deveLancarExcecaoQuandoCardapioNaoEncontrado() {
        when(cardapioItemRepository.findById(atualizarCardapioItemBatchInput.id())).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> atualizarCardapioItensBatchUseCaseTest.atualizarEmLote(List.of(atualizarCardapioItemBatchInput)));

        assertEquals("Item de cardápio com ID: 1 não encontrado.", exception.getMessage());
    }
}
