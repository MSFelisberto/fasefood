package br.com.fiap.fasefood.application.usecace.cardapio;

import br.com.fiap.fasefood.application.usecase.cardapio.ListarCardapiosUseCaseImpl;
import br.com.fiap.fasefood.core.domain.entities.Cardapio;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioRepository;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CardapioResponseDTO;
import br.com.fiap.fasefood.infra.controller.mapper.cardapio.CardapioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    void deveListarCardapioComSucesso() {
        try(MockedStatic<CardapioMapper> cardapioMapper = mockStatic(CardapioMapper.class)) {
            Long restauranteID = 1L;
            CardapioResponseDTO cardapioResponseDTO = mock(CardapioResponseDTO.class);
            Pageable pageable = PageRequest.of(0, 10);
            Cardapio item = mock(Cardapio.class);
            Page<Cardapio> page = new PageImpl<>(List.of(item));

            when(cardapioRepository.findByRestauranteId(restauranteID, pageable)).thenReturn(page);
            cardapioMapper.when(() -> CardapioMapper.toResponseDTO(item)).thenReturn(cardapioResponseDTO);

            Page<CardapioResponseDTO> result = listarCardapiosUseCase.listar(restauranteID, pageable);

            assertNotNull(result);
            assertEquals(1, result.getTotalElements());
        }
    }

}
