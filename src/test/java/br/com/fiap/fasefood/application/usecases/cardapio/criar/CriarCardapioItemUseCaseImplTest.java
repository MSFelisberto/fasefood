package br.com.fiap.fasefood.application.usecases.cardapio.criar;

import br.com.fiap.fasefood.core.entities.Cardapio;
import br.com.fiap.fasefood.core.entities.CardapioItem;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.gateways.CardapioItemRepository;
import br.com.fiap.fasefood.core.gateways.CardapioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CriarCardapioItemUseCaseImplTest {

    private CriarCardapioItemUseCaseImpl criarCardapioItemUseCase;
    private CardapioItemRepository cardapioItemRepository;
    private CardapioRepository cardapioRepository;

    private CriarCardapioItemInput criarCardapioItemInput;
    private Cardapio cardapio;

    @BeforeEach
    public void setUp() {
        cardapioItemRepository = mock(CardapioItemRepository.class);
        cardapioRepository = mock(CardapioRepository.class);
        criarCardapioItemUseCase = new CriarCardapioItemUseCaseImpl(cardapioItemRepository,cardapioRepository);

        criarCardapioItemInput = mock(CriarCardapioItemInput.class);
        cardapio = mock(Cardapio.class);
    }
    @Test
    public void deveCriarComSucesso() {
        when(cardapioRepository.findById(criarCardapioItemInput.cardapioId())).thenReturn(Optional.of(cardapio));
        when(cardapioItemRepository.salvar(any(CardapioItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

        criarCardapioItemUseCase.criar(criarCardapioItemInput);

        verify(cardapioItemRepository, times(1)).salvar(any());
        verify(cardapioRepository, times(1)).findById(criarCardapioItemInput.cardapioId());
    }
    @Test
    public void deveLancarExcecaoQuandoCardapioNaoEncontrado(){
        when(cardapioRepository.findById(criarCardapioItemInput.cardapioId())).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> criarCardapioItemUseCase.criar(criarCardapioItemInput));

        assertEquals("Cardápio com ID: 0 não encontrado.", exception.getMessage());
    }
}
