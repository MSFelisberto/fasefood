package br.com.fiap.fasefood.application.usecases.restaurante.listar;

import br.com.fiap.fasefood.application.usecases.restaurante.RestauranteOutput;
import br.com.fiap.fasefood.application.usecases.restaurante.mappers.RestauranteOutputMapper;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PageOutput;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PaginationInput;
import br.com.fiap.fasefood.application.usecases.usuario.UsuarioOutput;
import br.com.fiap.fasefood.core.entities.Endereco;
import br.com.fiap.fasefood.core.entities.Restaurante;
import br.com.fiap.fasefood.core.gateways.RestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalTime;
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
    private RestauranteOutput restauranteOutput;

    @BeforeEach
    public void setUp() {
        restauranteRepository = mock(RestauranteRepository.class);
        listarRestaurantesUseCase = new ListarRestaurantesUseCaseImpl(restauranteRepository);

        UsuarioOutput usuarioOutput = new UsuarioOutput(1L, "nome", "fiap@fiap.com.br", "fiap");
        restauranteOutput = new RestauranteOutput(1L, "teste", mock(Endereco.class), "tipoCozinha", LocalTime.MIN, LocalTime.MAX, usuarioOutput);
    }

    @Test
    public void deveListarRestaurantePaginadoComSucesso() {
        try(MockedStatic<RestauranteOutputMapper> restauranteMapper = mockStatic(RestauranteOutputMapper.class)) {

            Restaurante restaurante = mock(Restaurante.class);
            PaginationInput pageable = mock(PaginationInput.class);
            PageOutput<Restaurante> page =  new PageOutput<>(List.of(restaurante), 0, 10, 1 , 1L);

            when(restauranteRepository.listarTodosAtivos(pageable)).thenReturn(page);
            restauranteMapper.when(()->RestauranteOutputMapper.toRestauranteOutput(restaurante)).thenReturn(restauranteOutput);
            PageOutput<RestauranteOutput> responseDTOS = listarRestaurantesUseCase.listar(pageable);

            assertNotNull(responseDTOS);
            assertEquals(10, responseDTOS.size());
            assertEquals(restauranteOutput, responseDTOS.content().getFirst());

        }
    }
}
