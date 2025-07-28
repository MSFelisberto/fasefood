package br.com.fiap.fasefood.application.usecace.cardapio;

import br.com.fiap.fasefood.application.usecase.cardapio.AtualizarCardapioItensBatchUseCaseImpl;
import br.com.fiap.fasefood.core.domain.entities.Cardapio;
import br.com.fiap.fasefood.core.domain.entities.CardapioItem;
import br.com.fiap.fasefood.core.domain.entities.Endereco;
import br.com.fiap.fasefood.core.domain.entities.Restaurante;
import br.com.fiap.fasefood.core.domain.entities.TipoUsuario;
import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioItemRepository;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CardapioItemResponseDTO;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.UpdateCardapioItemDTO;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.UpdateCardapioItemRequestDTO;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.UpdateCardapioItemsBatchDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AtualizarCardapioItensBatchUseCaseImplTest {

    @InjectMocks
    private AtualizarCardapioItensBatchUseCaseImpl atualizarCardapioItensBatchUseCaseTest;

    private CardapioItemRepository cardapioItemRepository;

    @BeforeEach
    public void setUp() {
        cardapioItemRepository = Mockito.mock(CardapioItemRepository.class);
        atualizarCardapioItensBatchUseCaseTest = new AtualizarCardapioItensBatchUseCaseImpl(cardapioItemRepository);
    }


    @Test
    public void deveAtualizarComSucesso() {
        UpdateCardapioItemsBatchDTO updateCardapioItemDTO = buildUpdateCardapioItemsBatchDTO();
        CardapioItem cardapioItem = buildCardapioItem();
        when(cardapioItemRepository.findById(1L)).thenReturn(Optional.of(cardapioItem));
        when(cardapioItemRepository.salvar(any())).thenReturn(any());

        List<CardapioItemResponseDTO> response = atualizarCardapioItensBatchUseCaseTest.atualizarEmLote(updateCardapioItemDTO);

        verify(cardapioItemRepository, times(1)).salvar(any());
    }

    @Test
    public void deveLancarExcecao_QuandoCardapioNaoEncontrado() {
        UpdateCardapioItemsBatchDTO updateCardapioItemDTO = buildUpdateCardapioItemsBatchDTO();

        when(cardapioItemRepository.findById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            atualizarCardapioItensBatchUseCaseTest.atualizarEmLote(updateCardapioItemDTO);
        });

        assertEquals("Item de cardápio com ID: 1 não encontrado.", exception.getMessage());
    }


    private UpdateCardapioItemsBatchDTO buildUpdateCardapioItemsBatchDTO (){
        return new UpdateCardapioItemsBatchDTO(List.of(buildUpdateCardapioItemDTO()));

    }

    private UpdateCardapioItemRequestDTO buildUpdateCardapioItemDTO(){
        BigDecimal bigDecimal = new BigDecimal(15);
        return new UpdateCardapioItemRequestDTO(1L,"teste update","validando update",bigDecimal, false,"testePathFoto");
    }

    private CardapioItem buildCardapioItem(){
        Cardapio cardapio = buildCardapio();
        BigDecimal bigDecimal = new BigDecimal(10);
        return new CardapioItem(1L, cardapio, "teste item cardapio", "coverage",bigDecimal, true,"testePath", true );
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
