package br.com.fiap.fasefood.application.usecace.cardapio;

import br.com.fiap.fasefood.application.usecase.cardapio.ListarCardapiosUseCaseImpl;
import br.com.fiap.fasefood.core.domain.entities.Cardapio;
import br.com.fiap.fasefood.core.domain.entities.Endereco;
import br.com.fiap.fasefood.core.domain.entities.Restaurante;
import br.com.fiap.fasefood.core.domain.entities.TipoUsuario;
import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioRepository;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CardapioResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListarCardapiosUseCaseImplTest {

    @InjectMocks
    private ListarCardapiosUseCaseImpl listarCardapiosUseCase;

    private CardapioRepository cardapioRepository;

    @BeforeEach
    public void setUp() {
        cardapioRepository = mock(CardapioRepository.class);
        listarCardapiosUseCase = new ListarCardapiosUseCaseImpl(cardapioRepository);
    }

    @Test
    void deveListarCardapio() {
        Long restauranteID = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        Cardapio item = buildCardapio();
        Page<Cardapio> page = new PageImpl<>(List.of(item));

        when(cardapioRepository.findByRestauranteId(restauranteID, pageable)).thenReturn(page);

        Page<CardapioResponseDTO> result = listarCardapiosUseCase.listar(restauranteID, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    private Cardapio buildCardapio(){
        Restaurante restaurante = buildRestaurante();
        return new Cardapio(1L, restaurante, "teste nome", "teste descrição", true );
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
