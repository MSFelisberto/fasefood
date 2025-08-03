package br.com.fiap.fasefood.application.usecases.cardapio.criar;

import br.com.fiap.fasefood.application.usecases.cardapio.mappers.CardapioOutputMapper;
import br.com.fiap.fasefood.core.entities.Cardapio;
import br.com.fiap.fasefood.core.entities.CardapioItem;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.gateways.CardapioItemRepository;
import br.com.fiap.fasefood.core.gateways.CardapioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CriarCardapioItemsBatchUseCaseImplTest {

    private CriarCardapioItemsBatchUseCaseImpl criarCardapioItemsBatchUseCaseTest;
    private CardapioRepository cardapioRepository;
    private CardapioItemRepository cardapioItemRepository;

    private CriarCardapioItemsBatchInput criarCardapioItemsBatchInput;
    private Cardapio cardapio;
    private CriarCardapioItemOutput criarCardapioItemOutput;
    private ItemCardapioInput itemCardapioInput;

    @BeforeEach
    public void setUp() {
        cardapioItemRepository = mock(CardapioItemRepository.class);
        cardapioRepository = mock(CardapioRepository.class);
        criarCardapioItemsBatchUseCaseTest = new CriarCardapioItemsBatchUseCaseImpl(cardapioRepository,cardapioItemRepository);

        cardapio = mock(Cardapio.class);
        criarCardapioItemOutput = mock(CriarCardapioItemOutput.class);
        itemCardapioInput = new ItemCardapioInput("teste", "desc", BigDecimal.valueOf(10.0), true, "Path");
        criarCardapioItemsBatchInput = new CriarCardapioItemsBatchInput(1L, List.of(itemCardapioInput));
    }


    @Test
    public void deveCriarEmLoteComSucesso() {
        try(MockedStatic<CardapioOutputMapper> cardapioOutputMapper = mockStatic(CardapioOutputMapper.class)) {
            CardapioItem cardapioItemSalvo = mock(CardapioItem.class);

            when(cardapioRepository.findById(criarCardapioItemsBatchInput.cardapioId())).thenReturn(Optional.of(cardapio));
            when(cardapioItemRepository.salvar(any(CardapioItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

            cardapioOutputMapper.when(() -> CardapioOutputMapper.toCriarCardapioItemOutput(cardapioItemSalvo)).thenReturn(criarCardapioItemOutput);

            List<CriarCardapioItemOutput> response = criarCardapioItemsBatchUseCaseTest.criarEmLote(criarCardapioItemsBatchInput);

            verify(cardapioItemRepository, times(1)).salvar(any(CardapioItem.class));
            verify(cardapioRepository, times(1)).findById(criarCardapioItemsBatchInput.cardapioId());
            assertNotNull(response);
        }
    }

    @Test
    public void deveLancarExcecaoQuandoCardapioNaoEncontrado(){
        when(cardapioRepository.findById(criarCardapioItemsBatchInput.cardapioId())).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> criarCardapioItemsBatchUseCaseTest.criarEmLote(criarCardapioItemsBatchInput));

        assertEquals("Cardápio com ID: 1 não encontrado.", exception.getMessage());
    }

}
