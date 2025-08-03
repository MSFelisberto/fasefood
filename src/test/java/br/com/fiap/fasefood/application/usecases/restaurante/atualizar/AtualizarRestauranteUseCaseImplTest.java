package br.com.fiap.fasefood.application.usecases.restaurante.atualizar;

import br.com.fiap.fasefood.application.usecases.restaurante.RestauranteOutput;
import br.com.fiap.fasefood.application.usecases.shared.endereco.EnderecoInput;
import br.com.fiap.fasefood.core.entities.Endereco;
import br.com.fiap.fasefood.core.entities.Restaurante;
import br.com.fiap.fasefood.core.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.gateways.RestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.Optional;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class AtualizarRestauranteUseCaseImplTest {

    private AtualizarRestauranteUseCaseImpl atualizarRestauranteUseCase;
    private RestauranteRepository restauranteRepository;
    private UpdateRestauranteInput updateRestauranteInput;

    private static final Long RESTAURANTE_ID = 1L;

    @BeforeEach
    public void setUp() {
        restauranteRepository = mock(RestauranteRepository.class);
        atualizarRestauranteUseCase = new AtualizarRestauranteUseCaseImpl(restauranteRepository);

        EnderecoInput enderecoInput = new EnderecoInput("logradouro", "numero", "cep", "complemento", "bairro", "cidade", "uf");
        updateRestauranteInput = new UpdateRestauranteInput("teste", enderecoInput, "tipoCozinha", LocalTime.now(), LocalTime.now());
    }

    @Test
    void deveAtualizarRestaurante(){

        Restaurante restaurante = mock(Restaurante.class);
        Endereco endereco = mock(Endereco.class);
        Restaurante restauranteSalvo = mock(Restaurante.class);
        Usuario dono = mock(Usuario.class);

        when(restauranteRepository.findById(RESTAURANTE_ID)).thenReturn(Optional.of(restaurante));
        when(restaurante.getEndereco()).thenReturn(endereco);
        when(restauranteRepository.salvar(restaurante)).thenReturn(restauranteSalvo);
        when(restauranteSalvo.getDono()).thenReturn(dono);

        RestauranteOutput responseDTO = atualizarRestauranteUseCase.atualizar(RESTAURANTE_ID, updateRestauranteInput);

        assertNotNull(responseDTO);
        verify(restauranteRepository, times(1)).salvar(restaurante);
    }

    @Test
    public void deveLancarExcecaoQuandoRestauranteNaoEncontrado() {

        when(restauranteRepository.findById(RESTAURANTE_ID)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> atualizarRestauranteUseCase.atualizar(RESTAURANTE_ID, updateRestauranteInput));

        assertEquals("Restaurante não encontrado com ID: 1", exception.getMessage());
    }
}
