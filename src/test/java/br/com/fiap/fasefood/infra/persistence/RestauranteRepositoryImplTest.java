package br.com.fiap.fasefood.infra.persistence;

import br.com.fiap.fasefood.core.domain.entities.Restaurante;
import br.com.fiap.fasefood.infra.controller.mapper.restaurante.RestauranteEntityMapper;
import br.com.fiap.fasefood.infra.persistence.entities.RestauranteEntity;
import br.com.fiap.fasefood.infra.persistence.jpa.RestauranteJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RestauranteRepositoryImplTest {

    private RestauranteRepositoryImpl restauranteRepository;
    private RestauranteJpaRepository restauranteJpaRepository;

    private static final Long RESTAURANTE_ID = 1L;
    private Restaurante restaurante;
    private Restaurante restauranteSalvo;
    private RestauranteEntity restauranteEntity;
    private RestauranteEntity restauranteEntitySalvo;


    @BeforeEach
    public void setUp() {
        restauranteJpaRepository = mock(RestauranteJpaRepository.class);
        restauranteRepository = new RestauranteRepositoryImpl(restauranteJpaRepository);

        restaurante = mock(Restaurante.class);
        restauranteSalvo = mock(Restaurante.class);
        restauranteEntity = mock(RestauranteEntity.class);
        restauranteEntitySalvo = mock(RestauranteEntity.class);
    }

    @Test
    public void deveSalvarRestauranteComSucesso(){
        try(MockedStatic<RestauranteEntityMapper> restauranteEntityMapper = mockStatic(RestauranteEntityMapper.class)){

            restauranteEntityMapper.when(()-> RestauranteEntityMapper.toEntity(restaurante)).thenReturn(restauranteEntity);
            when(restauranteJpaRepository.save(restauranteEntity)).thenReturn(restauranteEntitySalvo);
            restauranteEntityMapper.when(()-> RestauranteEntityMapper.toDomain(restauranteEntitySalvo)).thenReturn(restauranteSalvo);

            Restaurante response = restauranteRepository.salvar(restaurante);

            assertNotNull(response);
            assertEquals(response, restauranteSalvo);
            verify(restauranteJpaRepository,times(1)).save(restauranteEntity);
        }
    }
    @Test
    public void deveRetornarRestauranteById(){
        try(MockedStatic<RestauranteEntityMapper> restauranteEntityMapper = mockStatic(RestauranteEntityMapper.class)){

            Optional<Restaurante> restauranteOptional = Optional.of(restaurante);
            when(restauranteJpaRepository.findByIdAndAtivoTrue(RESTAURANTE_ID)).thenReturn(Optional.of(restauranteEntity));
            restauranteEntityMapper.when(()-> RestauranteEntityMapper.toDomain(restauranteEntity)).thenReturn(restaurante);

            Optional<Restaurante> response = restauranteRepository.findById(RESTAURANTE_ID);
            assertNotNull(response);
            assertTrue(response.isPresent());
            assertEquals(response, restauranteOptional);
        }
    }

    @Test
    public void deveListarTodosRestaurantesAtivos(){
        try(MockedStatic<RestauranteEntityMapper> restauranteEntityMapper = mockStatic(RestauranteEntityMapper.class)) {

            Pageable pageable = PageRequest.of(0, 10);
            Page<RestauranteEntity> pageEntity = new PageImpl<>(List.of(restauranteEntity));
            Page<Restaurante> page = new PageImpl<>(List.of(restaurante));


            when(restauranteJpaRepository.findAllByAtivoTrue(pageable)).thenReturn(pageEntity);
            restauranteEntityMapper.when(()-> RestauranteEntityMapper.toDomain(restauranteEntity)).thenReturn(restaurante);
            Page<Restaurante> response = restauranteRepository.listarTodosAtivos(pageable);

            assertNotNull(response);
            assertEquals(response, page);
            verify(restauranteJpaRepository,times(1)).findAllByAtivoTrue(pageable);

        }
    }
}
