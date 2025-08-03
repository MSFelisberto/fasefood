package br.com.fiap.fasefood.application.usecases.cardapio.criar;

import br.com.fiap.fasefood.core.entities.Cardapio;
import br.com.fiap.fasefood.core.entities.CardapioItem;
import br.com.fiap.fasefood.core.entities.Restaurante;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.gateways.CardapioItemRepository;
import br.com.fiap.fasefood.core.gateways.CardapioRepository;
import br.com.fiap.fasefood.core.gateways.RestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CriarCardapioUseCaseImplTest {

    private CriarCardapioUseCaseImpl criarCardapioUseCaseTest;

    private CardapioRepository cardapioRepository;
    private RestauranteRepository restauranteRepository;
    private CardapioItemRepository cardapioItemRepository;
    private CriarCardapioInput criarCardapioInput;
    private CriarCardapioItemInput criarCardapioItemInput;

    @BeforeEach
    public void setUp() {
        cardapioRepository = mock(CardapioRepository.class);
        cardapioItemRepository = mock(CardapioItemRepository.class);
        restauranteRepository = mock(RestauranteRepository.class);

        criarCardapioUseCaseTest = new CriarCardapioUseCaseImpl(cardapioRepository, restauranteRepository, cardapioItemRepository);

        criarCardapioItemInput = new CriarCardapioItemInput(1L, "teste", "desc", BigDecimal.valueOf(10.0), true, "path");
        List<CriarCardapioItemInput> itemInputList = List.of(criarCardapioItemInput);
        criarCardapioInput = new CriarCardapioInput(1L, "teste", "desc", itemInputList);
    }

    @Test
    public void deveCriarCardapioComSucesso() {
        Restaurante restaurante = mock(Restaurante.class);
        when(restauranteRepository.findById(criarCardapioInput.restauranteId())).thenReturn(Optional.of(restaurante));
        when(cardapioRepository.salvar(any(Cardapio.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(cardapioItemRepository.salvar(any(CardapioItem.class))).thenAnswer(invocation -> invocation.getArgument(0));


        CriarCardapioOutput criarCardapioOutput = criarCardapioUseCaseTest.criar(criarCardapioInput);

        verify(restauranteRepository, times(1)).findById(criarCardapioInput.restauranteId());
        verify(cardapioRepository,times(1)).salvar(any(Cardapio.class));
        verify(cardapioItemRepository,times(1)).salvar(any(CardapioItem.class));

    }
    @Test
    public void deveLancarExcecaoQuandoRestauranteNaoEncontrado() {

        when(restauranteRepository.findById(criarCardapioInput.restauranteId())).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> criarCardapioUseCaseTest.criar(criarCardapioInput));
        assertEquals("Restaurante com ID: 1 não encontrado.", exception.getMessage());
    }
}
