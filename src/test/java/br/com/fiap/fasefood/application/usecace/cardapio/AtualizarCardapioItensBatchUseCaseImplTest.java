package br.com.fiap.fasefood.application.usecace.cardapio;

import br.com.fiap.fasefood.application.usecases.cardapio.atualizar.AtualizarCardapioItemBatchInput;
import br.com.fiap.fasefood.application.usecases.cardapio.atualizar.AtualizarCardapioItensBatchUseCaseImpl;
import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioItemOutput;
import br.com.fiap.fasefood.core.entities.Cardapio;
import br.com.fiap.fasefood.core.entities.CardapioItem;
import br.com.fiap.fasefood.core.entities.Endereco;
import br.com.fiap.fasefood.core.entities.Restaurante;
import br.com.fiap.fasefood.core.entities.TipoUsuario;
import br.com.fiap.fasefood.core.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.gateways.CardapioItemRepository;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CardapioItemResponseDTO;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.UpdateCardapioItemRequestDTO;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.UpdateCardapioItemsBatchDTO;
import br.com.fiap.fasefood.infra.controllers.mapper.cardapio.CardapioItemMapper;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
        UpdateCardapioItemsBatchDTO updateDto = buildUpdateCardapioItemsBatchDTO();
        List<AtualizarCardapioItemBatchInput> inputs = CardapioItemMapper.toAtualizarCardapioItemBatchInputList(updateDto);

        CardapioItem cardapioItem = buildCardapioItem();
        when(cardapioItemRepository.findById(1L)).thenReturn(Optional.of(cardapioItem));
        when(cardapioItemRepository.salvar(any())).thenReturn(cardapioItem);

        List<CriarCardapioItemOutput> response = atualizarCardapioItensBatchUseCaseTest.atualizarEmLote(inputs);

        verify(cardapioItemRepository, times(1)).salvar(any());
        assertNotNull(response);
        assertFalse(response.isEmpty());
    }

    @Test
    public void deveLancarExcecao_QuandoCardapioNaoEncontrado() {
        UpdateCardapioItemsBatchDTO updateDto = buildUpdateCardapioItemsBatchDTO();
        List<AtualizarCardapioItemBatchInput> inputs = CardapioItemMapper.toAtualizarCardapioItemBatchInputList(updateDto);

        when(cardapioItemRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            atualizarCardapioItensBatchUseCaseTest.atualizarEmLote(inputs);
        });

        assertEquals("Item de cardápio com ID: 1 não encontrado.", exception.getMessage());
    }

    private UpdateCardapioItemsBatchDTO buildUpdateCardapioItemsBatchDTO (){
        return new UpdateCardapioItemsBatchDTO(List.of(buildUpdateCardapioItemRequestDTO()));

    }

    private UpdateCardapioItemRequestDTO buildUpdateCardapioItemRequestDTO(){
        BigDecimal bigDecimal = new BigDecimal(15);
        return new UpdateCardapioItemRequestDTO(1L,"teste update","validando update",bigDecimal, false,"testePathFoto");
    }

    private CardapioItem buildCardapioItem(){
        Cardapio cardapio = buildCardapio();
        BigDecimal bigDecimal = new BigDecimal(10);
        return CardapioItem.create(1L, cardapio, "teste item cardapio", "coverage",bigDecimal, true,"testePath", true );
    }

    private Cardapio buildCardapio(){
        Restaurante restaurante = buildRestaurante();
        return Cardapio.create(1L, restaurante, "teste", "teste coverage", true );
    }

    private Restaurante buildRestaurante(){
        Endereco endereco = buildEndereco();
        Usuario dono = buildUsuario();
        return Restaurante.create(1L, "teste", endereco, "teste", LocalTime.now(),LocalTime.now(),dono, true);
    }

    private Endereco buildEndereco(){
        return Endereco.criarEndereco(1L, "logradouro", "01", "0000001", "complemento", "bairro","cidade", "uf");
    }

    private Usuario buildUsuario(){
        Endereco endereco = buildEndereco();
        TipoUsuario tipoUsuario = buildTipoUsuario();
        return Usuario.criarUsuario(1L, "teste","teste@teste.com", "dono", "123", LocalDate.now(),endereco, tipoUsuario, true);
    }

    private TipoUsuario buildTipoUsuario() {
        return new TipoUsuario(1L, "teste tipo usuario");
    }
}
