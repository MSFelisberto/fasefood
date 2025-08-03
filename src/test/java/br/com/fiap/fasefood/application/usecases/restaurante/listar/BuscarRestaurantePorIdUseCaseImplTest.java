package br.com.fiap.fasefood.application.usecases.restaurante.listar;

import br.com.fiap.fasefood.application.usecases.restaurante.RestauranteOutput;
import br.com.fiap.fasefood.core.entities.Restaurante;
import br.com.fiap.fasefood.core.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.gateways.RestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class BuscarRestaurantePorIdUseCaseImplTest {

    private BuscarRestaurantePorIdUseCaseImpl buscarRestaurantePorIdUseCase;
    private RestauranteRepository restauranteRepository;

    private static final Long RESTAURANTE_ID = 1L;


    @BeforeEach
    public void setUp() {
        restauranteRepository = mock(RestauranteRepository.class);
        buscarRestaurantePorIdUseCase = new BuscarRestaurantePorIdUseCaseImpl(restauranteRepository);

    }

    @Test
    void deveRetornarRestaurantePorID(){
        Restaurante restaurante = mock(Restaurante.class);
        Usuario dono = mock(Usuario.class);

        when(restauranteRepository.findById(RESTAURANTE_ID)).thenReturn(Optional.of(restaurante));
        when(restaurante.getDono()).thenReturn(dono);

        RestauranteOutput restauranteOutput = buscarRestaurantePorIdUseCase.buscarPorId(RESTAURANTE_ID);

        assertNotNull(restauranteOutput);
        verify(restauranteRepository, times(1)).findById(RESTAURANTE_ID);

    }

    @Test
    public void deveLancarExcecaoQuandoRestauranteNaoEncontrado() {

        when(restauranteRepository.findById(RESTAURANTE_ID)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> buscarRestaurantePorIdUseCase.buscarPorId(RESTAURANTE_ID));

        assertEquals("Restaurante não encontrado com ID: 1", exception.getMessage());
    }

}
