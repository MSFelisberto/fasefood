package br.com.fiap.fasefood.infra.persistence;

import br.com.fiap.fasefood.core.domain.entities.TipoUsuario;
import br.com.fiap.fasefood.infra.controller.mapper.TipoUsuarioMapper;
import br.com.fiap.fasefood.infra.persistence.entities.TipoUsuarioEntity;
import br.com.fiap.fasefood.infra.persistence.jpa.TipoUsuarioJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

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
public class TipoUsuarioRepositoryImplTest {

    private TipoUsuarioRepositoryImpl tipoUsuarioRepository;
    private TipoUsuarioJpaRepository tipoUsuarioJpaRepository;

    private static final Long TIPO_USUARIO_ID = 1L;
    private static final String TIPO_USUARIO_NOME = "DONO";
    private TipoUsuario tipoUsuario;
    private TipoUsuarioEntity tipoUsuarioEntity;
    private TipoUsuarioEntity tipoUsuarioEntitySalvo;
    private TipoUsuario tipoUsuarioSalvo;


    @BeforeEach
    public void setUp() {
        tipoUsuarioJpaRepository = mock(TipoUsuarioJpaRepository.class);
        tipoUsuarioRepository = new TipoUsuarioRepositoryImpl(tipoUsuarioJpaRepository);

        tipoUsuario = mock(TipoUsuario.class);
        tipoUsuarioEntity = mock(TipoUsuarioEntity.class);
        tipoUsuarioSalvo = mock(TipoUsuario.class);
        tipoUsuarioEntitySalvo = mock(TipoUsuarioEntity.class);
    }
    @Test
    public void deveRetornarTipoUsuarioById(){
        try(MockedStatic<TipoUsuarioMapper> tipoUsuarioMapper = mockStatic(TipoUsuarioMapper.class)){

            Optional<TipoUsuario> tipoUsuarioOptional = Optional.of(tipoUsuario);
            when(tipoUsuarioJpaRepository.findById(TIPO_USUARIO_ID)).thenReturn(Optional.of(tipoUsuarioEntity));
            tipoUsuarioMapper.when(()-> TipoUsuarioMapper.toDomain(tipoUsuarioEntity)).thenReturn(tipoUsuario);

            Optional<TipoUsuario> response = tipoUsuarioRepository.findById(TIPO_USUARIO_ID);
            assertNotNull(response);
            assertTrue(response.isPresent());
            assertEquals(response, tipoUsuarioOptional);
        }
    }

    @Test
    public void deveRetornarTipoUsuarioByNome(){
        try(MockedStatic<TipoUsuarioMapper> tipoUsuarioMapper = mockStatic(TipoUsuarioMapper.class)){

            Optional<TipoUsuario> tipoUsuarioOptional = Optional.of(tipoUsuario);
            when(tipoUsuarioJpaRepository.findByNome(TIPO_USUARIO_NOME)).thenReturn(Optional.of(tipoUsuarioEntity));
            tipoUsuarioMapper.when(()-> TipoUsuarioMapper.toDomain(tipoUsuarioEntity)).thenReturn(tipoUsuario);

            Optional<TipoUsuario> response = tipoUsuarioRepository.findByNome(TIPO_USUARIO_NOME);
            assertNotNull(response);
            assertTrue(response.isPresent());
            assertEquals(response, tipoUsuarioOptional);
        }

    }

    @Test
    public void deveListarTodosTipoUsuario(){
        try(MockedStatic<TipoUsuarioMapper> tipoUsuarioMapper = mockStatic(TipoUsuarioMapper.class)){

            List<TipoUsuario> tipoUsuarioList = List.of(tipoUsuario);
            when(tipoUsuarioJpaRepository.findAll()).thenReturn(List.of(tipoUsuarioEntity));
            tipoUsuarioMapper.when(()-> TipoUsuarioMapper.toDomain(tipoUsuarioEntity)).thenReturn(tipoUsuario);

            List<TipoUsuario> response = tipoUsuarioRepository.findAll();

            assertNotNull(response);
            verify(tipoUsuarioJpaRepository,times(1)).findAll();
            assertEquals(response, tipoUsuarioList);
        }
    }

    @Test
    public void deveSalvarTipoUsuario(){
        try(MockedStatic<TipoUsuarioMapper> tipoUsuarioMapper = mockStatic(TipoUsuarioMapper.class)) {

            tipoUsuarioMapper.when(()-> TipoUsuarioMapper.toEntity(tipoUsuario)).thenReturn(tipoUsuarioEntity);
            when(tipoUsuarioJpaRepository.save(tipoUsuarioEntity)).thenReturn(tipoUsuarioEntitySalvo);
            tipoUsuarioMapper.when(()-> TipoUsuarioMapper.toDomain(tipoUsuarioEntitySalvo)).thenReturn(tipoUsuarioSalvo);

            TipoUsuario response = tipoUsuarioRepository.salvar(tipoUsuario);

            assertNotNull(response);
            verify(tipoUsuarioJpaRepository,times(1)).save(tipoUsuarioEntity);
            assertEquals(response, tipoUsuarioSalvo);
        }
    }
}
