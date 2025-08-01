package br.com.fiap.fasefood.application.usecace.cardapio;

import br.com.fiap.fasefood.application.usecase.cardapio.CriarCardapioItemsBatchUseCaseImpl;
import br.com.fiap.fasefood.core.domain.entities.Cardapio;
import br.com.fiap.fasefood.core.domain.entities.CardapioItem;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioItemRepository;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioRepository;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CardapioItemResponseDTO;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CreateCardapioItemsBatchDTO;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.ItensCreateCardapioItemDTO;
import br.com.fiap.fasefood.infra.controller.mapper.cardapio.CardapioItemMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CriarCardapioItemsBatchUseCaseImplTest {

    private CriarCardapioItemsBatchUseCaseImpl criarCardapioItemsBatchUseCaseTest;
    private CardapioRepository cardapioRepository;
    private CardapioItemRepository cardapioItemRepository;

    private CreateCardapioItemsBatchDTO createCardapioItemsBatchDTO;
    private Cardapio cardapio;
    private ItensCreateCardapioItemDTO itensCreateCardapioItemDTO;

    @BeforeEach
    public void setUp() {
        cardapioItemRepository = mock(CardapioItemRepository.class);
        cardapioRepository = mock(CardapioRepository.class);

        criarCardapioItemsBatchUseCaseTest = new CriarCardapioItemsBatchUseCaseImpl(cardapioRepository,cardapioItemRepository);
        createCardapioItemsBatchDTO = mock(CreateCardapioItemsBatchDTO.class);
        cardapio = mock(Cardapio.class);
        itensCreateCardapioItemDTO = mock(ItensCreateCardapioItemDTO.class);

    }


    @Test
    public void deveCriarEmLoteComSucesso() {
        try(MockedStatic<CardapioItemMapper> cardapioItemMapper = mockStatic(CardapioItemMapper.class)) {
            CardapioItem cardapioItem = mock(CardapioItem.class);
            CardapioItem cardapioItemSalvo = mock(CardapioItem.class);

            when(cardapioRepository.findById(createCardapioItemsBatchDTO.cardapioId())).thenReturn(Optional.of(cardapio));
            when(createCardapioItemsBatchDTO.itens()).thenReturn(Collections.singletonList(itensCreateCardapioItemDTO));
            cardapioItemMapper.when(() -> CardapioItemMapper.toDomain(itensCreateCardapioItemDTO,cardapio)).thenReturn(cardapioItem);
            when(cardapioItemRepository.salvar(cardapioItem)).thenReturn(cardapioItemSalvo);

            List<CardapioItemResponseDTO> response = criarCardapioItemsBatchUseCaseTest.criarEmLote(createCardapioItemsBatchDTO);

            verify(cardapioItemRepository, times(1)).salvar(cardapioItem);
            verify(cardapioRepository, times(1)).findById(createCardapioItemsBatchDTO.cardapioId());

            assertNotNull(response);
        }
    }

    @Test
    public void deveLancarExcecaoQuandoCardapioNaoEncontrado(){
        when(cardapioRepository.findById(createCardapioItemsBatchDTO.cardapioId())).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> criarCardapioItemsBatchUseCaseTest.criarEmLote(createCardapioItemsBatchDTO));

        assertEquals("Cardápio com ID: 0 não encontrado.", exception.getMessage());
    }
}
