package br.com.fiap.fasefood.infra.config;

import br.com.fiap.fasefood.application.usecases.cardapio.atualizar.AtualizarCardapioItemUseCase;
import br.com.fiap.fasefood.application.usecases.cardapio.atualizar.AtualizarCardapioItensBatchUseCase;
import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioItemUseCase;
import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioItemsBatchUseCase;
import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioUseCase;
import br.com.fiap.fasefood.application.usecases.cardapio.deletar.RemoverCardapioItemUseCase;
import br.com.fiap.fasefood.application.usecases.cardapio.deletar.RemoverItensCardapioUseCase;
import br.com.fiap.fasefood.application.usecases.cardapio.listar.ListarCardapioItensUseCase;
import br.com.fiap.fasefood.application.usecases.cardapio.listar.ListarCardapiosUseCase;
import br.com.fiap.fasefood.core.gateways.CardapioItemRepository;
import br.com.fiap.fasefood.core.gateways.CardapioRepository;
import br.com.fiap.fasefood.core.gateways.RestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class CardapioConfigTest {


    private final CardapioRepository cardapioRepo = mock(CardapioRepository.class);
    private final RestauranteRepository restauranteRepo = mock(RestauranteRepository.class);
    private final CardapioItemRepository itemRepo = mock(CardapioItemRepository.class);

    private CardapioConfig config;

    @BeforeEach
    public void setup() {
        config = new CardapioConfig();
    }

    @Test
    public void deveInstanciarCriarCardapioUseCase() {
        CriarCardapioUseCase useCase = config.criarCardapioUseCase(cardapioRepo, restauranteRepo, itemRepo);
        assertNotNull(useCase);
    }

    @Test
    public void deveInstanciarListarCardapiosUseCase() {
        ListarCardapiosUseCase useCase = config.listarCardapiosUseCase(cardapioRepo);
        assertNotNull(useCase);
    }

    @Test
    public void deveInstanciarCriarCardapioItemUseCase() {
        CriarCardapioItemUseCase useCase = config.criarCardapioItemUseCase(itemRepo, cardapioRepo);
        assertNotNull(useCase);
    }
    @Test
    public void deveInstanciarCriarCardapioItemsBatchUseCase() {
        CriarCardapioItemsBatchUseCase useCase = config.criarCardapioItemsBatchUseCase(cardapioRepo, itemRepo);
        assertNotNull(useCase);
    }

    @Test
    public void deveInstanciarListarCardapioItensUseCase() {
        ListarCardapioItensUseCase useCase = config.listarCardapioItensUseCase(itemRepo);
        assertNotNull(useCase);
    }

    @Test
    public void deveInstanciarAtualizarCardapioItemUseCase() {
        AtualizarCardapioItemUseCase useCase = config.atualizarCardapioItemUseCase(itemRepo);
        assertNotNull(useCase);
    }
    @Test
    public void deveInstanciarAtualizarCardapioItensBatchUseCase() {
        AtualizarCardapioItensBatchUseCase useCase = config.atualizarCardapioItensBatchUseCase(itemRepo);
        assertNotNull(useCase);
    }

    @Test
    public void deveInstanciarRemoverCardapioItemUseCase() {
        RemoverCardapioItemUseCase useCase = config.removerCardapioItemUseCase(itemRepo);
        assertNotNull(useCase);
    }

    @Test
    public void deveInstanciarRemoverItensCardapioUseCase() {
        RemoverItensCardapioUseCase useCase = config.removerItensCardapioUseCase(itemRepo);
        assertNotNull(useCase);
    }
}
