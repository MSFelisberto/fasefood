package br.com.fiap.fasefood.application.usecases.restaurante.deletar;

import br.com.fiap.fasefood.core.entities.Restaurante;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.gateways.RestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class DeletarRestauranteUseCaseImplTest {

    private DeletarRestauranteUseCaseImpl deletarRestauranteUseCase;
    private RestauranteRepository restauranteRepository;

    private static final Long RESTAURANTE_ID = 1L;

    @BeforeEach
    public void setUp() {
        restauranteRepository = mock(RestauranteRepository.class);
        deletarRestauranteUseCase = new DeletarRestauranteUseCaseImpl(restauranteRepository);
    }
    @Test
    public void deveDeletarRestauranteComSucesso() {
        Restaurante restaurante = mock(Restaurante.class);

        when(restauranteRepository.findById(RESTAURANTE_ID)).thenReturn(Optional.of(restaurante));
        deletarRestauranteUseCase.deletar(RESTAURANTE_ID);
        verify(restauranteRepository, times(1)).salvar(restaurante);
    }


    @Test
    public void deveLancarExcecaoResourceNotFoundException() {
        when(restauranteRepository.findById(RESTAURANTE_ID)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> deletarRestauranteUseCase.deletar(RESTAURANTE_ID));
        assertEquals("Restaurante não encontrado com ID: 1", exception.getMessage());
    }
}
