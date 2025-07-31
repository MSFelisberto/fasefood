package br.com.fiap.fasefood.application.usecace.cardapio;

import br.com.fiap.fasefood.application.usecases.cardapio.atualizar.AtualizarCardapioItemUseCaseImpl;
import br.com.fiap.fasefood.core.entities.Cardapio;
import br.com.fiap.fasefood.core.entities.CardapioItem;
import br.com.fiap.fasefood.core.entities.Endereco;
import br.com.fiap.fasefood.core.entities.Restaurante;
import br.com.fiap.fasefood.core.entities.TipoUsuario;
import br.com.fiap.fasefood.core.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.gateways.CardapioItemRepository;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CardapioItemResponseDTO;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.UpdateCardapioItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AtualizarCardapioItemUseCaseImplTest {

    @InjectMocks
    private AtualizarCardapioItemUseCaseImpl atualizarCardapioItemUseCaseTest;

    private CardapioItemRepository cardapioItemRepository;

    @BeforeEach
    public void setUp() {
        cardapioItemRepository = Mockito.mock(CardapioItemRepository.class);
        atualizarCardapioItemUseCaseTest = new AtualizarCardapioItemUseCaseImpl(cardapioItemRepository);
    }

    @Test
    public void deveAtualizarComSucesso() {
        UpdateCardapioItemDTO updateCardapioItemDTO = buildUpdateCardapioItemDTO();
        CardapioItem cardapioItem = buildCardapioItem();
        when(cardapioItemRepository.findById(1L)).thenReturn(Optional.of(cardapioItem));
        when(cardapioItemRepository.salvar(any())).thenReturn(any());

        CardapioItemResponseDTO response = atualizarCardapioItemUseCaseTest.atualizar(1L, updateCardapioItemDTO);

        verify(cardapioItemRepository, times(1)).salvar(any());
    }

    @Test
    public void deveLancarExcecao_QuandoCardapioNaoEncontrado() {
        UpdateCardapioItemDTO updateCardapioItemDTO = buildUpdateCardapioItemDTO();

        when(cardapioItemRepository.findById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            atualizarCardapioItemUseCaseTest.atualizar(1L, updateCardapioItemDTO);
        });

        assertEquals("Item de cardápio com ID: 1 não encontrado.", exception.getMessage());
    }
    private Endereco buildEndereco(){
        return Endereco.criarEndereco(1L, "logradouro", "01", "0000001", "complemento", "bairro","cidade", "uf");
    }
    private Restaurante buildRestaurante(){
        Endereco endereco = buildEndereco();
        Usuario dono = buildUsuario();
        return  new Restaurante(1L, "teste", endereco, "teste", LocalTime.now(),LocalTime.now(),dono, true);
    }

    private Cardapio buildCardapio(){
        Restaurante restaurante = buildRestaurante();
        return new Cardapio(1L, restaurante, "teste", "teste coverage", true );
    }

    private CardapioItem buildCardapioItem(){
        Cardapio cardapio = buildCardapio();
        BigDecimal bigDecimal = new BigDecimal(10);
        return new CardapioItem(1L, cardapio, "teste item cardapio", "coverage",bigDecimal, true,"testePath", true );
    }

    private TipoUsuario buildTipoUsuario() {
        return new TipoUsuario(1L, "teste tipo usuario");
    }

    private Usuario buildUsuario(){
        Endereco endereco = buildEndereco();
        TipoUsuario tipoUsuario = buildTipoUsuario();
        return Usuario.criarUsuario(1L, "teste","teste@teste.com", "dono", "123", LocalDate.now(),endereco, tipoUsuario, true);
    }

    private UpdateCardapioItemDTO buildUpdateCardapioItemDTO(){
        BigDecimal bigDecimal = new BigDecimal(15);
        return new UpdateCardapioItemDTO("teste update","validando update",bigDecimal, false,"testePathFoto");
    }


}
