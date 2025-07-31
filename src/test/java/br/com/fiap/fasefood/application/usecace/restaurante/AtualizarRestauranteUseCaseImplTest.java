package br.com.fiap.fasefood.application.usecace.restaurante;

import br.com.fiap.fasefood.application.usecase.restaurante.AtualizarRestauranteUseCaseImpl;
import br.com.fiap.fasefood.core.domain.entities.Endereco;
import br.com.fiap.fasefood.core.domain.entities.Restaurante;
import br.com.fiap.fasefood.core.domain.entities.TipoUsuario;
import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.RestauranteRepository;
import br.com.fiap.fasefood.infra.controller.dto.EnderecoDTO;
import br.com.fiap.fasefood.infra.controller.dto.restaurante.UpdateRestauranteDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class AtualizarRestauranteUseCaseImplTest {

    public static final LocalTime HORARIO_ABERTURA = LocalTime.of(8, 0);
    public static final LocalTime HORARIO_FECHAMENTO = LocalTime.of(18, 0);
    public static final LocalDate DATA_ULTIMA_ATUALIZACAO = LocalDate.of(2025, 7, 30);

    private RestauranteRepository restauranteRepository;

    @InjectMocks
    private AtualizarRestauranteUseCaseImpl atualizarRestauranteUseCase;

    @BeforeEach
    public void setUp() {
        restauranteRepository = mock(RestauranteRepository.class);
        atualizarRestauranteUseCase = new AtualizarRestauranteUseCaseImpl(restauranteRepository);
    }

    @Test
    void deveAtualizarRestaurante(){
        Long restauranteID = 1L;
        Restaurante restaurante = mock(Restaurante.class);
        UpdateRestauranteDTO updateRestauranteDTO = mock(UpdateRestauranteDTO.class);

        when(restauranteRepository.findById(restauranteID)).thenReturn(Optional.of(restaurante));
        when(updateRestauranteDTO.endereco()).thenReturn(buildEnderecoDTO());
        when(restaurante.getEndereco()).thenReturn(buildEndereco());
        when(restauranteRepository.salvar(restaurante)).thenReturn(buildRestaurante());

        atualizarRestauranteUseCase.atualizar(restauranteID, updateRestauranteDTO);

        verify(restauranteRepository, times(1)).salvar(restaurante);
    }

    @Test
    public void deveLancarExcecaoQuandoRestauranteNaoEncontrado() {
        Long restauranteID = 1L;
        UpdateRestauranteDTO updateRestauranteDTO = mock(UpdateRestauranteDTO.class);

        when(restauranteRepository.findById(restauranteID)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            atualizarRestauranteUseCase.atualizar(restauranteID, updateRestauranteDTO);
        });

        assertEquals("Restaurante não encontrado com ID: 1", exception.getMessage());
    }


    private EnderecoDTO buildEnderecoDTO(){
        return new EnderecoDTO("teste logradouro", "123", "1111111", "teste complemento", "tste bairro", "teste cidade", "teste if");
    }
    private Restaurante buildRestaurante(){
        return new Restaurante(1L,"teste nome", buildEndereco(), "teste tipo cozinha", HORARIO_ABERTURA, HORARIO_FECHAMENTO, buildUsuario(), true );
    }
    private Endereco buildEndereco(){
        return new Endereco(1L, "logradouro", "01", "0000001", "complemento", "bairro","cidade", "uf");
    }
    private Usuario buildUsuario(){
        return  new Usuario(1L, "teste","teste@teste.com", "dono", "123", DATA_ULTIMA_ATUALIZACAO,buildEndereco(), buildTipoUsuario(), true);
    }
    private TipoUsuario buildTipoUsuario() {
        return new TipoUsuario(1L, "teste tipo usuario");
    }
}
