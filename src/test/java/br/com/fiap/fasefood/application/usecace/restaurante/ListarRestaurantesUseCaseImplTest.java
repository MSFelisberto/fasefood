package br.com.fiap.fasefood.application.usecace.restaurante;

import br.com.fiap.fasefood.application.usecase.restaurante.ListarRestaurantesUseCaseImpl;
import br.com.fiap.fasefood.core.domain.entities.Restaurante;
import br.com.fiap.fasefood.core.usecase.gateways.RestauranteRepository;
import br.com.fiap.fasefood.infra.controller.dto.restaurante.RestauranteResponseDTO;
import br.com.fiap.fasefood.infra.controller.mapper.restaurante.RestauranteMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class ListarRestaurantesUseCaseImplTest {

    private ListarRestaurantesUseCaseImpl listarRestaurantesUseCase;
    private RestauranteRepository restauranteRepository;

    @BeforeEach
    public void setUp() {

        restauranteRepository = mock(RestauranteRepository.class);
        listarRestaurantesUseCase = new ListarRestaurantesUseCaseImpl(restauranteRepository);
    }

    @Test
    public void deveListarRestaurantePaginadoComSucesso() {
        try(MockedStatic<RestauranteMapper> restauranteMapper = mockStatic(RestauranteMapper.class)) {

            Pageable pageable = PageRequest.of(0, 10);

            Restaurante restaurante = mock(Restaurante.class);
            Page<Restaurante> page = new PageImpl<>(List.of(restaurante));
            RestauranteResponseDTO restauranteResponseDTO = mock(RestauranteResponseDTO.class);

            when(restauranteRepository.listarTodosAtivos(pageable)).thenReturn(page);
            restauranteMapper.when(()->RestauranteMapper.toResponseDTO(restaurante)).thenReturn(restauranteResponseDTO);
            Page<RestauranteResponseDTO> responseDTOS = listarRestaurantesUseCase.listar(pageable);

            assertNotNull(responseDTOS);
            assertEquals(1, responseDTOS.getTotalElements());
        }
    }
}
