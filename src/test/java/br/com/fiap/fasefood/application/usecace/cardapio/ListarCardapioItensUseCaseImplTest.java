package br.com.fiap.fasefood.application.usecace.cardapio;

import br.com.fiap.fasefood.application.usecase.cardapio.ListarCardapioItensUseCaseImpl;
import br.com.fiap.fasefood.core.domain.entities.CardapioItem;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioItemRepository;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CardapioItemResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.when;

import java.util.List;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class ListarCardapioItensUseCaseImplTest {

    @InjectMocks
    private ListarCardapioItensUseCaseImpl listarCardapioItensUseCaseTest;

    @Mock
    private CardapioItemRepository cardapioItemRepository;


    @BeforeEach
    public void setUp() {
        cardapioItemRepository = mock(CardapioItemRepository.class);
        listarCardapioItensUseCaseTest = new ListarCardapioItensUseCaseImpl(cardapioItemRepository);
    }


    @Test
    void deveListarItensDoCardapio() {
        Long cardapioId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        CardapioItem item = new CardapioItem();
        Page<CardapioItem> page = new PageImpl<>(List.of(item));

        when(cardapioItemRepository.findByCardapioId(cardapioId, pageable))
                .thenReturn(page);

        Page<CardapioItemResponseDTO> result = listarCardapioItensUseCaseTest.listar(cardapioId, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }
}
