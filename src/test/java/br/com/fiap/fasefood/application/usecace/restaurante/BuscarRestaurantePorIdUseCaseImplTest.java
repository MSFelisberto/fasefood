package br.com.fiap.fasefood.application.usecace.restaurante;

import br.com.fiap.fasefood.application.usecase.restaurante.BuscarRestaurantePorIdUseCaseImpl;
import br.com.fiap.fasefood.core.domain.entities.Restaurante;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.RestauranteRepository;
import br.com.fiap.fasefood.infra.controller.dto.restaurante.RestauranteResponseDTO;
import br.com.fiap.fasefood.infra.controller.mapper.restaurante.RestauranteMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;
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
        try (MockedStatic<RestauranteMapper> restauranteMapper = mockStatic(RestauranteMapper.class)){

        Restaurante restaurante = mock(Restaurante.class);
        RestauranteResponseDTO restauranteResponseDTO  = mock(RestauranteResponseDTO.class);

        when(restauranteRepository.findById(RESTAURANTE_ID)).thenReturn(Optional.of(restaurante));
        restauranteMapper.when(() -> RestauranteMapper.toResponseDTO(restaurante)).thenReturn(restauranteResponseDTO);

        buscarRestaurantePorIdUseCase.buscarPorId(RESTAURANTE_ID);

        restauranteMapper.verify(() -> RestauranteMapper.toResponseDTO(restaurante));}

    }

    @Test
    public void deveLancarExcecaoQuandoRestauranteNaoEncontrado() {

        when(restauranteRepository.findById(RESTAURANTE_ID)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> buscarRestaurantePorIdUseCase.buscarPorId(RESTAURANTE_ID));

        assertEquals("Restaurante não encontrado com ID: 1", exception.getMessage());
    }

}
