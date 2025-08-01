package br.com.fiap.fasefood.application.usecace.cardapio;

import br.com.fiap.fasefood.application.usecase.cardapio.CriarCardapioItemUseCaseImpl;
import br.com.fiap.fasefood.core.domain.entities.Cardapio;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioItemRepository;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioRepository;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CreateCardapioItemDTO;
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

    private CreateCardapioItemDTO createCardapioItemDTO;
    private Cardapio cardapio;

    @BeforeEach
    public void setUp() {
        cardapioItemRepository = mock(CardapioItemRepository.class);
        cardapioRepository = mock(CardapioRepository.class);
        criarCardapioItemUseCase = new CriarCardapioItemUseCaseImpl(cardapioRepository,cardapioItemRepository);

        createCardapioItemDTO = mock(CreateCardapioItemDTO.class);
        cardapio = mock(Cardapio.class);
    }
    @Test
    public void deveCriarComSucesso() {
        when(cardapioRepository.findById(createCardapioItemDTO.cardapioId())).thenReturn(Optional.of(cardapio));

        criarCardapioItemUseCase.criar(createCardapioItemDTO);

        verify(cardapioItemRepository, times(1)).salvar(any());
        verify(cardapioRepository, times(1)).findById(createCardapioItemDTO.cardapioId());
    }
    @Test
    public void deveLancarExcecaoQuandoCardapioNaoEncontrado(){
        when(cardapioRepository.findById(createCardapioItemDTO.cardapioId())).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> criarCardapioItemUseCase.criar(createCardapioItemDTO));

        assertEquals("Cardápio com ID: 0 não encontrado.", exception.getMessage());
    }
}
