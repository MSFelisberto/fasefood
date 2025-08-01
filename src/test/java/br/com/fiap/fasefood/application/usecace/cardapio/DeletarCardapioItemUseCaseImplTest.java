package br.com.fiap.fasefood.application.usecace.cardapio;

import br.com.fiap.fasefood.application.usecase.cardapio.DeletarCardapioItemUseCaseImpl;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DeletarCardapioItemUseCaseImplTest {

    private DeletarCardapioItemUseCaseImpl deletarCardapioItemUseCase;
    private CardapioItemRepository cardapioItemRepository;

    @BeforeEach
    public void setUp() {
        cardapioItemRepository = mock(CardapioItemRepository.class);
        deletarCardapioItemUseCase = new DeletarCardapioItemUseCaseImpl(cardapioItemRepository);
    }

    @Test
    public void deveDeletarComSucesso() {
        deletarCardapioItemUseCase.deletar(anyLong());
        verify(cardapioItemRepository, times(1)).deletar(anyLong());

    }
}
