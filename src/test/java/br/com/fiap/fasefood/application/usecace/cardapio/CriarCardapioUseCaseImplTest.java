package br.com.fiap.fasefood.application.usecace.cardapio;

import br.com.fiap.fasefood.application.usecase.cardapio.CriarCardapioUseCaseImpl;
import br.com.fiap.fasefood.core.domain.entities.Cardapio;
import br.com.fiap.fasefood.core.domain.entities.CardapioItem;
import br.com.fiap.fasefood.core.domain.entities.Restaurante;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioItemRepository;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioRepository;
import br.com.fiap.fasefood.core.usecase.gateways.RestauranteRepository;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CreateCardapioDTO;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.ItensCreateCardapioItemDTO;
import br.com.fiap.fasefood.infra.controller.mapper.cardapio.CardapioItemMapper;
import br.com.fiap.fasefood.infra.controller.mapper.cardapio.CardapioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CriarCardapioUseCaseImplTest {

    private CriarCardapioUseCaseImpl criarCardapioUseCaseTeste;

    private CardapioRepository cardapioRepository;
    private RestauranteRepository restauranteRepository;
    private CardapioItemRepository cardapioItemRepository;

    @BeforeEach
    public void setUp() {
        cardapioRepository = mock(CardapioRepository.class);
        cardapioItemRepository = mock(CardapioItemRepository.class);
        restauranteRepository = mock(RestauranteRepository.class);

        criarCardapioUseCaseTeste = new CriarCardapioUseCaseImpl(cardapioRepository, restauranteRepository, cardapioItemRepository);
    }

    @Test
    public void deveCriarCardapioComSucesso() {
        try(MockedStatic<CardapioMapper> cardapioMapper = mockStatic(CardapioMapper.class);
        MockedStatic<CardapioItemMapper> cardapioItemMapper = mockStatic(CardapioItemMapper.class)){

        Long restauranteID = 1L;
        Restaurante restaurante = mock(Restaurante.class);
        Cardapio cardapio = mock(Cardapio.class);
        Cardapio novoCardapio = mock(Cardapio.class);
        CreateCardapioDTO createCardapioDTO =  buildCreateCardapioDTO();
        CardapioItem cardapioItem = mock(CardapioItem.class);

        when(restauranteRepository.findById(restauranteID)).thenReturn(Optional.of(restaurante));
        cardapioMapper.when(() -> CardapioMapper.toDomain(createCardapioDTO, restaurante)).thenReturn(cardapio);
        when(cardapioRepository.salvar(cardapio)).thenReturn(novoCardapio);

        cardapioItemMapper.when(() -> CardapioItemMapper.toDomain(buildItensCreateCardapioItemDTO(), novoCardapio)).thenReturn(cardapioItem);
        when(cardapioItemRepository.salvar(cardapioItem)).thenReturn(cardapioItem);

        criarCardapioUseCaseTeste.criar(createCardapioDTO);

        verify(restauranteRepository, times(1)).findById(restauranteID);
        verify(cardapioItemRepository, times(1)).salvar(cardapioItem);}

    }
    @Test
    public void deveLancarExcecaoQuandoRestauranteNaoEncontrado() {
        Long restauranteID = 1L;
        CreateCardapioDTO createCardapioDTO =  buildCreateCardapioDTO();

        when(restauranteRepository.findById(restauranteID)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> criarCardapioUseCaseTeste.criar(createCardapioDTO));

        assertEquals("Restaurante com ID: 1 não encontrado.", exception.getMessage());
    }


    private CreateCardapioDTO buildCreateCardapioDTO(){
        return new CreateCardapioDTO(1L, "teste", "teste descricao", List.of(buildItensCreateCardapioItemDTO()));
    }
    private ItensCreateCardapioItemDTO buildItensCreateCardapioItemDTO(){
        BigDecimal bigDecimal = new BigDecimal(10);
        return new ItensCreateCardapioItemDTO("teste", "descricao teste", bigDecimal, true, "testePath" );
    }
}
