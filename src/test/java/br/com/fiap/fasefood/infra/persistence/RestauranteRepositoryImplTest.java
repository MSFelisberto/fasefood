package br.com.fiap.fasefood.infra.persistence;

import br.com.fiap.fasefood.application.usecases.shared.paginacao.PageOutput;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PaginationInput;
import br.com.fiap.fasefood.core.entities.Restaurante;
import br.com.fiap.fasefood.infra.controllers.mapper.restaurante.RestauranteEntityMapper;
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
import org.springframework.data.domain.Sort;

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
    void deveRetornarRestaurantePaginadosAtivos() {
        try (MockedStatic<RestauranteEntityMapper> mapperMock = mockStatic(RestauranteEntityMapper.class)) {

            PaginationInput pageable = new PaginationInput(0, 2, "NOME", "ASC");
            Pageable springPageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, "NOME"));

            RestauranteEntity entity1 = mock(RestauranteEntity.class);
            RestauranteEntity entity2 = mock(RestauranteEntity.class);

            List<RestauranteEntity> entities = List.of(entity1, entity2);
            Page<RestauranteEntity> page = new PageImpl<>(entities, springPageable, 10);

            when(restauranteJpaRepository.findAllByAtivoTrue(springPageable)).thenReturn(page);

            Restaurante restaurante1 = mock(Restaurante.class);
            Restaurante restaurante2 = mock(Restaurante.class);

            mapperMock.when(() -> RestauranteEntityMapper.toDomain(entity1)).thenReturn(restaurante1);
            mapperMock.when(() -> RestauranteEntityMapper.toDomain(entity2)).thenReturn(restaurante2);

            PageOutput<Restaurante> result = restauranteRepository.listarTodosAtivos(pageable);

            assertEquals(5, result.totalPages());
            assertEquals(10, result.totalElements());
        }
    }
}