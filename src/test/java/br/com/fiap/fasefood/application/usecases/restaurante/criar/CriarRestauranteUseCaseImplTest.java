package br.com.fiap.fasefood.application.usecases.restaurante.criar;

import br.com.fiap.fasefood.application.usecases.restaurante.RestauranteOutput;
import br.com.fiap.fasefood.application.usecases.shared.endereco.EnderecoInput;
import br.com.fiap.fasefood.core.entities.Restaurante;
import br.com.fiap.fasefood.core.entities.TipoUsuario;
import br.com.fiap.fasefood.core.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.RegraDeNegocioException;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.gateways.RestauranteRepository;
import br.com.fiap.fasefood.core.gateways.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CriarRestauranteUseCaseImplTest {

    private CriarRestauranteUseCaseImpl criarRestauranteUseCase;
    private RestauranteRepository restauranteRepository;
    private UsuarioRepository usuarioRepository;


    private CriarRestauranteInput criarRestauranteInput;
    private Usuario dono;
    private TipoUsuario tipoUsuario;

    private static final String NOME_DONO_RESTAURANTE = "DONO_RESTAURANTE";
    private static final String NOME_USUARIO = "USUARIO";

    @BeforeEach
    public void setUp() {

        restauranteRepository = mock(RestauranteRepository.class);
        usuarioRepository = mock(UsuarioRepository.class);
        criarRestauranteUseCase = new CriarRestauranteUseCaseImpl(restauranteRepository,usuarioRepository);

        criarRestauranteInput = new CriarRestauranteInput("nome", mock(EnderecoInput.class), "tipoCozinha", LocalTime.now(), LocalTime.now(), 1L);
        dono = mock(Usuario.class);
        tipoUsuario = mock(TipoUsuario.class);
    }

    @Test
    public void deveCriarRestauranteComSucesso() {

        when(usuarioRepository.findById(criarRestauranteInput.donoId())).thenReturn(Optional.of(dono));
        when(dono.getTipoUsuario()).thenReturn(tipoUsuario);
        when(tipoUsuario.getNome()).thenReturn(NOME_DONO_RESTAURANTE);
        when(restauranteRepository.salvar(any(Restaurante.class))).thenAnswer(invocation -> invocation.getArgument(0));

        RestauranteOutput response = criarRestauranteUseCase.criar(criarRestauranteInput);

        assertNotNull(response);
        verify(restauranteRepository, times(1)).salvar(any(Restaurante.class));

    }

    @Test
    public void deveLancarExcecaoResourceNotFoundException() {

        when(usuarioRepository.findById(criarRestauranteInput.donoId())).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> criarRestauranteUseCase.criar(criarRestauranteInput));
        assertEquals("Usuário com ID: 1 não encontrado.", exception.getMessage());
    }

    @Test
    public void deveLancarExcecaoRegraDeNegocioException() {

        when(usuarioRepository.findById(criarRestauranteInput.donoId())).thenReturn(Optional.of(dono));
        when(dono.getTipoUsuario()).thenReturn(tipoUsuario);
        when(tipoUsuario.getNome()).thenReturn(NOME_USUARIO);

        RegraDeNegocioException exception = assertThrows(RegraDeNegocioException.class, () -> criarRestauranteUseCase.criar(criarRestauranteInput));
        assertEquals("Apenas usuários do tipo DONO_RESTAURANTE podem ser donos de um restaurante.", exception.getMessage());
    }
}
