package br.com.fiap.fasefood.application.usecace.cardapio;

import br.com.fiap.fasefood.application.usecase.cardapio.CriarCardapioItemsBatchUseCaseImpl;
import br.com.fiap.fasefood.core.domain.entities.Cardapio;
import br.com.fiap.fasefood.core.domain.entities.Endereco;
import br.com.fiap.fasefood.core.domain.entities.Restaurante;
import br.com.fiap.fasefood.core.domain.entities.TipoUsuario;
import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioItemRepository;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioRepository;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CardapioItemResponseDTO;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CreateCardapioItemsBatchDTO;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.ItensCreateCardapioItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CriarCardapioItemsBatchUseCaseImplTest {

    @InjectMocks
    private CriarCardapioItemsBatchUseCaseImpl criarCardapioItemsBatchUseCaseTest;

    private CardapioRepository cardapioRepository;

    private CardapioItemRepository cardapioItemRepository;

    @BeforeEach
    public void setUp() {
        cardapioItemRepository = mock(CardapioItemRepository.class);
        cardapioRepository = mock(CardapioRepository.class);

        criarCardapioItemsBatchUseCaseTest = new CriarCardapioItemsBatchUseCaseImpl(cardapioRepository,cardapioItemRepository);
    }


    @Test
    public void deveCriarEmLoteComSucesso() {
        CreateCardapioItemsBatchDTO createCardapioItemsBatchDTO = buildCreateCardapioItemsBatchDTO();
        when(cardapioRepository.findById(createCardapioItemsBatchDTO.cardapioId())).thenReturn(Optional.of(buildCardapio()));

        List<CardapioItemResponseDTO> response = criarCardapioItemsBatchUseCaseTest.criarEmLote(createCardapioItemsBatchDTO);

        verify(cardapioItemRepository, times(1)).salvar(any());
        verify(cardapioRepository, times(1)).findById(createCardapioItemsBatchDTO.cardapioId());

        assertNotNull(response);

    }

    @Test
    public void deveLancarExcecao_QuandoCardapioNaoEncontrado(){
        CreateCardapioItemsBatchDTO createCardapioItemsBatchDTO = buildCreateCardapioItemsBatchDTO();
        when(cardapioRepository.findById(createCardapioItemsBatchDTO.cardapioId())).thenReturn(Optional.of(buildCardapio()));

        when(cardapioRepository.findById(createCardapioItemsBatchDTO.cardapioId())).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> criarCardapioItemsBatchUseCaseTest.criarEmLote(createCardapioItemsBatchDTO));

        assertEquals("Cardápio com ID: 1 não encontrado.", exception.getMessage());
    }

    private CreateCardapioItemsBatchDTO buildCreateCardapioItemsBatchDTO(){
        return  new CreateCardapioItemsBatchDTO(1L, buildItensCreateCardapio());
    }
    private List<ItensCreateCardapioItemDTO> buildItensCreateCardapio(){
        return List.of(buildItensCreateCardapioItemDTO());
    }

    private ItensCreateCardapioItemDTO buildItensCreateCardapioItemDTO(){
        BigDecimal bigDecimal = new BigDecimal(10);
        return new ItensCreateCardapioItemDTO("teste", "descricao teste", bigDecimal, true, "testePath" );
    }
    private Cardapio buildCardapio(){
        Restaurante restaurante = buildRestaurante();
        return new Cardapio(1L, restaurante, "teste", "teste coverage", true );
    }
    private Restaurante buildRestaurante(){
        Endereco endereco = buildEndereco();
        Usuario dono = buildUsuario();
        return  new Restaurante(1L, "teste", endereco, "teste", LocalTime.now(),LocalTime.now(),dono, true);
    }

    private Endereco buildEndereco(){
        return new Endereco(1L, "logradouro", "01", "0000001", "complemento", "bairro","cidade", "uf");
    }

    private Usuario buildUsuario(){
        Endereco endereco = buildEndereco();
        TipoUsuario tipoUsuario = buildTipoUsuario();
        return  new Usuario(1L, "teste","teste@teste.com", "dono", "123", LocalDate.now(),endereco, tipoUsuario, true);
    }

    private TipoUsuario buildTipoUsuario() {
        return new TipoUsuario(1L, "teste tipo usuario");
    }
}
