package br.com.fiap.fasefood.application.usecace.cardapio;

import br.com.fiap.fasefood.application.usecase.cardapio.CriarCardapioItemUseCaseImpl;
import br.com.fiap.fasefood.core.domain.entities.Cardapio;
import br.com.fiap.fasefood.core.domain.entities.Endereco;
import br.com.fiap.fasefood.core.domain.entities.Restaurante;
import br.com.fiap.fasefood.core.domain.entities.TipoUsuario;
import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioItemRepository;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioRepository;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CreateCardapioItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CriarCardapioItemUseCaseImplTest {

    @InjectMocks
    private CriarCardapioItemUseCaseImpl criarCardapioItemUseCase;

    private CardapioItemRepository cardapioItemRepository;
    private CardapioRepository cardapioRepository;

    @BeforeEach
    public void setUp() {
        cardapioItemRepository = mock(CardapioItemRepository.class);
        cardapioRepository = mock(CardapioRepository.class);
        criarCardapioItemUseCase = new CriarCardapioItemUseCaseImpl(cardapioRepository,cardapioItemRepository);
    }
    @Test
    public void deveCriarComSucesso() {
        CreateCardapioItemDTO createCardapioItemDTO = buildCreateCardapioItemDTO();
        when(cardapioRepository.findById(createCardapioItemDTO.cardapioId())).thenReturn(Optional.of(buildCardapio()));

        criarCardapioItemUseCase.criar(createCardapioItemDTO);
        verify(cardapioItemRepository, times(1)).salvar(any());
        verify(cardapioRepository, times(1)).findById(createCardapioItemDTO.cardapioId());
    }
    @Test
    public void deveLancarExcecaoQuandoCardapioNaoEncontrado(){
        CreateCardapioItemDTO createCardapioItemDTO = buildCreateCardapioItemDTO();
        when(cardapioRepository.findById(createCardapioItemDTO.cardapioId())).thenReturn(Optional.of(buildCardapio()));

        when(cardapioRepository.findById(createCardapioItemDTO.cardapioId())).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> criarCardapioItemUseCase.criar(createCardapioItemDTO));

        assertEquals("Cardápio com ID: 1 não encontrado.", exception.getMessage());
    }

    private CreateCardapioItemDTO buildCreateCardapioItemDTO(){
        BigDecimal bigDecimal = new BigDecimal(10);
        return new CreateCardapioItemDTO(1L, "teste", "teste descricao", bigDecimal,true, "teste Path" );
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
