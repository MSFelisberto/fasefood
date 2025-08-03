package br.com.fiap.fasefood.application.usecases.cardapio.listar;

import br.com.fiap.fasefood.application.usecases.cardapio.mappers.CardapioOutputMapper;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PageOutput;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PaginationInput;
import br.com.fiap.fasefood.core.entities.Cardapio;
import br.com.fiap.fasefood.core.gateways.CardapioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListarCardapiosUseCaseImplTest {

    private ListarCardapiosUseCaseImpl listarCardapiosUseCase;
    private CardapioRepository cardapioRepository;

    @BeforeEach
    public void setUp() {
        cardapioRepository = mock(CardapioRepository.class);
        listarCardapiosUseCase = new ListarCardapiosUseCaseImpl(cardapioRepository);
    }

    @Test
    public void deveListarCardapioPorRestauranteId() {

        try (MockedStatic<CardapioOutputMapper> mockedMapper = mockStatic(CardapioOutputMapper.class)) {
            Long restauranteID = 1L;
            PaginationInput pageable = new PaginationInput(0, 10, "nome", "asc");

            Cardapio cardapio = mock(Cardapio.class);
            CardapioResponseOutput cardapioResponseOutput = new CardapioResponseOutput(1L, 1L, "teste", "desc");

            PageOutput<Cardapio> cardapioPageOutput = new PageOutput<>(
                    List.of(cardapio), 0, 10, 1, 1L
            );

            when(cardapioRepository.findByRestauranteId(restauranteID, pageable)).thenReturn(cardapioPageOutput);

            mockedMapper.when(() -> CardapioOutputMapper.toCardapioResponseOutput(cardapio))
                    .thenReturn(cardapioResponseOutput);

            PageOutput<CardapioResponseOutput> resultado = listarCardapiosUseCase.listar(restauranteID, pageable);


            assertEquals(1, resultado.content().size());
            assertEquals(cardapioResponseOutput, resultado.content().getFirst());
            assertEquals(0, resultado.currentPage());
            assertEquals(10, resultado.size());
            assertEquals(1, resultado.totalPages());
            assertEquals(1L, resultado.totalElements());
        }
    }

}
