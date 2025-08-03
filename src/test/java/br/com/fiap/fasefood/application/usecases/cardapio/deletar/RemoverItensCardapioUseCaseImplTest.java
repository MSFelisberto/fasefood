package br.com.fiap.fasefood.application.usecases.cardapio.deletar;

import br.com.fiap.fasefood.core.gateways.CardapioItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RemoverItensCardapioUseCaseImplTest {

    private RemoverItensCardapioUseCaseImpl removerItensCardapioUseCase;
    private CardapioItemRepository cardapioItemRepository;

    @BeforeEach
    public void setUp() {
        cardapioItemRepository = mock(CardapioItemRepository.class);
        removerItensCardapioUseCase = new RemoverItensCardapioUseCaseImpl(cardapioItemRepository);
    }

    @Test
    public void deveDeletarComSucesso() {
        removerItensCardapioUseCase.removerEmLote(List.of(anyLong()));
        verify(cardapioItemRepository, times(1)).remover(anyLong());

    }
}
