package br.com.fiap.fasefood.application.usecace.restaurante;

import br.com.fiap.fasefood.application.usecase.restaurante.CriarRestauranteUseCaseImpl;
import br.com.fiap.fasefood.core.domain.entities.Restaurante;
import br.com.fiap.fasefood.core.domain.entities.TipoUsuario;
import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.RegraDeNegocioException;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.RestauranteRepository;
import br.com.fiap.fasefood.core.usecase.gateways.UsuarioRepository;
import br.com.fiap.fasefood.infra.controller.dto.restaurante.CreateRestauranteDTO;
import br.com.fiap.fasefood.infra.controller.dto.restaurante.RestauranteResponseDTO;
import br.com.fiap.fasefood.infra.controller.mapper.restaurante.RestauranteMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CriarRestauranteUseCaseImplTest {

    @InjectMocks
    private CriarRestauranteUseCaseImpl criarRestauranteUseCase;

    private CreateRestauranteDTO createRestauranteDTO;
    private TipoUsuario tipoUsuario;

    private RestauranteRepository restauranteRepository;
    private UsuarioRepository usuarioRepository;
    private Usuario dono;

    private final static String NOME_DONO_RESTAURANTE = "DONO_RESTAURANTE";
    private final static String NOME_USUARIO = "USUARIO";

    @BeforeEach
    public void setUp() {

        restauranteRepository = mock(RestauranteRepository.class);
        usuarioRepository = mock(UsuarioRepository.class);
        criarRestauranteUseCase = new CriarRestauranteUseCaseImpl(restauranteRepository,usuarioRepository);

        createRestauranteDTO = mock(CreateRestauranteDTO.class);
        tipoUsuario = mock(TipoUsuario.class);
        dono = mock(Usuario.class);
    }

    @Test
    public void deveCriarRestauranteComSucesso() {

        try(MockedStatic<RestauranteMapper> restauranteMapper = mockStatic(RestauranteMapper.class)){
            Restaurante restaurante = mock(Restaurante.class);
            Restaurante restauranteSalvo = mock(Restaurante.class);
            RestauranteResponseDTO restauranteResponseDTO = mock(RestauranteResponseDTO.class);

            when(usuarioRepository.findById(createRestauranteDTO.donoId())).thenReturn(Optional.of(dono));
            when(dono.getTipoUsuario()).thenReturn(tipoUsuario);
            when(tipoUsuario.getNome()).thenReturn(NOME_DONO_RESTAURANTE);

            restauranteMapper.when(() -> RestauranteMapper.toDomain(createRestauranteDTO, dono)).thenReturn(restaurante);
            when(restauranteRepository.salvar(restaurante)).thenReturn(restauranteSalvo);
            restauranteMapper.when(() -> RestauranteMapper.toResponseDTO(restauranteSalvo)).thenReturn(restauranteResponseDTO);


            RestauranteResponseDTO response = criarRestauranteUseCase.criar(createRestauranteDTO);

            assertEquals(response, restauranteResponseDTO);
        }
    }

    @Test
    public void deveLancarExcecaoResourceNotFoundException() {

        when(usuarioRepository.findById(createRestauranteDTO.donoId())).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            criarRestauranteUseCase.criar(createRestauranteDTO);
        });
        assertEquals("Usuário com ID: 0 não encontrado.", exception.getMessage());
    }

    @Test
    public void deveLancarExcecaoRegraDeNegocioException() {

        when(usuarioRepository.findById(createRestauranteDTO.donoId())).thenReturn(Optional.of(dono));
        when(dono.getTipoUsuario()).thenReturn(tipoUsuario);
        when(tipoUsuario.getNome()).thenReturn(NOME_USUARIO);

        RegraDeNegocioException exception = assertThrows(RegraDeNegocioException.class, () -> {
            criarRestauranteUseCase.criar(createRestauranteDTO);
        });
        assertEquals("Apenas usuários do tipo DONO_RESTAURANTE podem ser donos de um restaurante.", exception.getMessage());
    }
}
