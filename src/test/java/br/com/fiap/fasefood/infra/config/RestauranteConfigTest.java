package br.com.fiap.fasefood.infra.config;

import br.com.fiap.fasefood.application.usecases.restaurante.atualizar.AtualizarRestauranteUseCase;
import br.com.fiap.fasefood.application.usecases.restaurante.criar.CriarRestauranteUseCase;
import br.com.fiap.fasefood.application.usecases.restaurante.deletar.DeletarRestauranteUseCase;
import br.com.fiap.fasefood.application.usecases.restaurante.listar.BuscarRestaurantePorIdUseCase;
import br.com.fiap.fasefood.application.usecases.restaurante.listar.ListarRestaurantesUseCase;
import br.com.fiap.fasefood.core.gateways.RestauranteRepository;
import br.com.fiap.fasefood.core.gateways.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class RestauranteConfigTest {

    private RestauranteConfig config;

    private final RestauranteRepository restauranteRepository = mock(RestauranteRepository.class);
    private final UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);


    @BeforeEach
    public void setup() {
        config = new RestauranteConfig();
    }


    @Test
    public void deveInstanciarCriarRestauranteUseCase() {
        CriarRestauranteUseCase useCase = config.criarRestauranteUseCase( restauranteRepository, usuarioRepository);
        assertNotNull(useCase);
    }


    @Test
    public void deveInstanciarAtualizarRestauranteUseCase() {
        AtualizarRestauranteUseCase useCase = config.atualizarRestauranteUseCase( restauranteRepository);
        assertNotNull(useCase);
    }

    @Test
    public void deveInstanciarDeletarRestauranteUseCase() {
        DeletarRestauranteUseCase useCase = config.deletarRestauranteUseCase( restauranteRepository);
        assertNotNull(useCase);
    }

    @Test
    public void deveInstanciarBuscarRestaurantePorIdUseCase() {
        BuscarRestaurantePorIdUseCase useCase = config.buscarRestaurantePorIdUseCase( restauranteRepository);
        assertNotNull(useCase);
    }

    @Test
    public void deveInstanciarListarRestaurantesUseCase() {
        ListarRestaurantesUseCase useCase = config.listarRestaurantesUseCase( restauranteRepository);
        assertNotNull(useCase);
    }

}
