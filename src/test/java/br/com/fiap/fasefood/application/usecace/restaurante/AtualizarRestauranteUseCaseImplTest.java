package br.com.fiap.fasefood.application.usecace.restaurante;

import br.com.fiap.fasefood.application.usecase.restaurante.AtualizarRestauranteUseCaseImpl;
import br.com.fiap.fasefood.core.domain.entities.Endereco;
import br.com.fiap.fasefood.core.domain.entities.Restaurante;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.RestauranteRepository;
import br.com.fiap.fasefood.infra.controller.dto.EnderecoDTO;
import br.com.fiap.fasefood.infra.controller.dto.restaurante.RestauranteResponseDTO;
import br.com.fiap.fasefood.infra.controller.dto.restaurante.UpdateRestauranteDTO;
import br.com.fiap.fasefood.infra.controller.mapper.restaurante.RestauranteMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class AtualizarRestauranteUseCaseImplTest {

    private AtualizarRestauranteUseCaseImpl atualizarRestauranteUseCase;
    private RestauranteRepository restauranteRepository;
    private UpdateRestauranteDTO updateRestauranteDTO;

    private static final Long RESTAURANTE_ID = 1L;

    @BeforeEach
    public void setUp() {
        restauranteRepository = mock(RestauranteRepository.class);
        atualizarRestauranteUseCase = new AtualizarRestauranteUseCaseImpl(restauranteRepository);

        updateRestauranteDTO = mock(UpdateRestauranteDTO.class);
    }

    @Test
    void deveAtualizarRestaurante(){
        try(MockedStatic<RestauranteMapper> restauranteMapper = mockStatic(RestauranteMapper.class)) {

            Restaurante restaurante = mock(Restaurante.class);
            EnderecoDTO enderecoDTO = mock(EnderecoDTO.class);
            Endereco endereco = mock(Endereco.class);
            Restaurante restauranteSalvo = mock(Restaurante.class);
            RestauranteResponseDTO restauranteResponseDTO = mock(RestauranteResponseDTO.class);

            when(restauranteRepository.findById(RESTAURANTE_ID)).thenReturn(Optional.of(restaurante));
            when(updateRestauranteDTO.endereco()).thenReturn(enderecoDTO);
            when(restaurante.getEndereco()).thenReturn(endereco);
            when(restauranteRepository.salvar(restaurante)).thenReturn(restauranteSalvo);
            restauranteMapper.when(()->RestauranteMapper.toResponseDTO(restauranteSalvo)).thenReturn(restauranteResponseDTO);

            RestauranteResponseDTO responseDTO = atualizarRestauranteUseCase.atualizar(RESTAURANTE_ID, updateRestauranteDTO);

            verify(restauranteRepository, times(1)).salvar(restaurante);
            assertEquals(responseDTO, restauranteResponseDTO);

        }
    }

    @Test
    public void deveLancarExcecaoQuandoRestauranteNaoEncontrado() {

        when(restauranteRepository.findById(RESTAURANTE_ID)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> atualizarRestauranteUseCase.atualizar(RESTAURANTE_ID, updateRestauranteDTO));

        assertEquals("Restaurante não encontrado com ID: 1", exception.getMessage());
    }
}
