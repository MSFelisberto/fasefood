package br.com.fiap.fasefood.application.usecace.restaurante;

import br.com.fiap.fasefood.application.usecase.restaurante.BuscarRestaurantePorIdUseCaseImpl;
import br.com.fiap.fasefood.core.domain.entities.Restaurante;
import br.com.fiap.fasefood.core.usecase.gateways.RestauranteRepository;
import br.com.fiap.fasefood.infra.controller.dto.restaurante.RestauranteResponseDTO;
import br.com.fiap.fasefood.infra.controller.mapper.cardapio.CardapioMapper;
import br.com.fiap.fasefood.infra.controller.mapper.restaurante.RestauranteMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class BuscarRestaurantePorIdUseCaseImplTest {

    @InjectMocks
    BuscarRestaurantePorIdUseCaseImpl buscarRestaurantePorIdUseCase;

    private RestauranteRepository restauranteRepository;


    @BeforeEach
    public void setUp() {
        restauranteRepository = mock(RestauranteRepository.class);
        buscarRestaurantePorIdUseCase = new BuscarRestaurantePorIdUseCaseImpl(restauranteRepository);

    }

    @Test
    void deveRetornarRestaurante(){
        try (MockedStatic<RestauranteMapper> restauranteMapper = mockStatic(RestauranteMapper.class)){

        Long restauranteID = 1L;
        Restaurante restaurante = mock(Restaurante.class);
        RestauranteResponseDTO restauranteResponseDTO  = mock(RestauranteResponseDTO.class);

        when(restauranteRepository.findById(restauranteID)).thenReturn(Optional.of(restaurante));
        restauranteMapper.when(() -> RestauranteMapper.toResponseDTO(restaurante)).thenReturn(restauranteResponseDTO);

        buscarRestaurantePorIdUseCase.buscarPorId(restauranteID);

        restauranteMapper.verify(() -> RestauranteMapper.toResponseDTO(restaurante));}

    }


}
