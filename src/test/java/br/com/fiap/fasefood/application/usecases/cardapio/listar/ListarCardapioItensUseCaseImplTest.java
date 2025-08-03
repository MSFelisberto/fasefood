package br.com.fiap.fasefood.application.usecases.cardapio.listar;

import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioItemOutput;
import br.com.fiap.fasefood.application.usecases.cardapio.mappers.CardapioOutputMapper;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PageOutput;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PaginationInput;
import br.com.fiap.fasefood.core.entities.CardapioItem;
import br.com.fiap.fasefood.core.gateways.CardapioItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.util.List;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class ListarCardapioItensUseCaseImplTest {


    private ListarCardapioItensUseCaseImpl listarCardapioItensUseCaseTest;
    private CardapioItemRepository cardapioItemRepository;


    @BeforeEach
    public void setUp() {
        cardapioItemRepository = mock(CardapioItemRepository.class);
        listarCardapioItensUseCaseTest = new ListarCardapioItensUseCaseImpl(cardapioItemRepository);
    }

    @Test
    public void deveListarItensPorCardapioId() {

        try (MockedStatic<CardapioOutputMapper> mockedMapper = mockStatic(CardapioOutputMapper.class)) {
            Long cardapioId = 1L;
            PaginationInput pageable = new PaginationInput(0, 10, "nome", "asc");

            CardapioItem item = mock(CardapioItem.class);
            CriarCardapioItemOutput cardapioItemOutput = mock(CriarCardapioItemOutput.class);

            PageOutput<CardapioItem> cardapioItemPageOutput = new PageOutput<>(
                    List.of(item), 0, 10, 1, 1L
            );

            when(cardapioItemRepository.findByCardapioId(cardapioId, pageable)).thenReturn(cardapioItemPageOutput);

            mockedMapper.when(() -> CardapioOutputMapper.toCriarCardapioItemOutput(item))
                    .thenReturn(cardapioItemOutput);

            PageOutput<CriarCardapioItemOutput> resultado = listarCardapioItensUseCaseTest.listar(cardapioId, pageable);


            assertEquals(1, resultado.content().size());
            assertEquals(cardapioItemOutput, resultado.content().getFirst());
            assertEquals(0, resultado.currentPage());
            assertEquals(10, resultado.size());
            assertEquals(1, resultado.totalPages());
            assertEquals(1L, resultado.totalElements());
        }
    }
}
