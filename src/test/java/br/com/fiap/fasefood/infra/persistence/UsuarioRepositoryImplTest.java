package br.com.fiap.fasefood.infra.persistence;

import br.com.fiap.fasefood.application.usecases.shared.paginacao.PageOutput;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PaginationInput;
import br.com.fiap.fasefood.core.entities.Usuario;
import br.com.fiap.fasefood.infra.controllers.mapper.UserEntityMapper;
import br.com.fiap.fasefood.infra.persistence.entities.UsuarioEntity;
import br.com.fiap.fasefood.infra.persistence.jpa.UserJpaRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioRepositoryImplTest {

    private UsuarioRepositoryImpl usuarioRepository;
    private UserJpaRepository userJpaRepository;


    private Usuario usuario;
    private UsuarioEntity usuarioEntity;
    private Usuario usuarioSalvo;
    private UsuarioEntity usuarioEntitySalvo;


    @BeforeEach
    public void setUp() {
        userJpaRepository = mock(UserJpaRepository.class);
        usuarioRepository = new UsuarioRepositoryImpl(userJpaRepository);

        usuario = mock(Usuario.class);
        usuarioEntity = mock(UsuarioEntity.class);
        usuarioSalvo = mock(Usuario.class);
        usuarioEntitySalvo = mock(UsuarioEntity.class);
    }


    @Test
    public void deveRetornarUsuarioByIdComSucesso(){
        try(MockedStatic<UserEntityMapper> userEntityMapper = mockStatic(UserEntityMapper.class)) {
            Long usuarioId = 1L;

            when(userJpaRepository.findByIdAndAtivoTrue(usuarioId)).thenReturn(Optional.of(usuarioEntity));
            userEntityMapper.when(()-> UserEntityMapper.toDomain(usuarioEntity)).thenReturn(usuario);

            Optional<Usuario> response = usuarioRepository.findById(usuarioId);

            assertNotNull(response);
            assertTrue(response.isPresent());
            assertEquals(response, Optional.of(usuario));
        }
    }

    @Test
    public void deveRetornarUsuarioByEmailComSucesso(){
        try(MockedStatic<UserEntityMapper> userEntityMapper = mockStatic(UserEntityMapper.class)) {
            String email = "testeFastFood@fiap.com.br";

            when(userJpaRepository.findByEmailAndAtivoTrue(email)).thenReturn(Optional.of(usuarioEntity));
            userEntityMapper.when(()-> UserEntityMapper.toDomain(usuarioEntity)).thenReturn(usuario);

            Optional<Usuario> response = usuarioRepository.findByEmail(email);

            assertNotNull(response);
            assertTrue(response.isPresent());
            assertEquals(response, Optional.of(usuario));
        }
    }


    @Test
    public void deveRetornarUsuarioByLoginComSucesso(){
        try(MockedStatic<UserEntityMapper> userEntityMapper = mockStatic(UserEntityMapper.class)) {
            String login = "testeFastFood";

            when(userJpaRepository.findByLoginAndAtivoTrue(login)).thenReturn(Optional.of(usuarioEntity));
            userEntityMapper.when(()-> UserEntityMapper.toDomain(usuarioEntity)).thenReturn(usuario);

            Optional<Usuario> response = usuarioRepository.findByLogin(login);

            assertNotNull(response);
            assertTrue(response.isPresent());
            assertEquals(response, Optional.of(usuario));
        }
    }

    @Test
    public void deveSalvarUsuarioComSucesso(){
        try(MockedStatic<UserEntityMapper> userEntityMapper = mockStatic(UserEntityMapper.class)) {

            userEntityMapper.when(()-> UserEntityMapper.toEntity(usuario)).thenReturn(usuarioEntity);
            when(userJpaRepository.save(usuarioEntity)).thenReturn(usuarioEntitySalvo);
            userEntityMapper.when(()-> UserEntityMapper.toDomain(usuarioEntitySalvo)).thenReturn(usuarioSalvo);

            Usuario response = usuarioRepository.salvar(usuario);

            assertNotNull(response);
            verify(userJpaRepository).save(usuarioEntity);
            assertEquals(response, usuarioSalvo);
        }
    }

    @Test
    public void deveListarTodosUsuarioAtivos(){
        try (MockedStatic<UserEntityMapper> mapperMock = mockStatic(UserEntityMapper.class)) {

            PaginationInput pageable = new PaginationInput(0, 2, "NOME", "ASC");
            Pageable springPageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, "NOME"));

            UsuarioEntity entity1 = mock(UsuarioEntity.class);
            UsuarioEntity entity2 = mock(UsuarioEntity.class);

            List<UsuarioEntity> entities = List.of(entity1, entity2);
            Page<UsuarioEntity> page = new PageImpl<>(entities, springPageable, 10);

            when(userJpaRepository.findAllByAtivoTrue(springPageable)).thenReturn(page);

            Usuario usuario1 = mock(Usuario.class);
            Usuario usuario2 = mock(Usuario.class);

            mapperMock.when(() -> UserEntityMapper.toDomain(entity1)).thenReturn(usuario1);
            mapperMock.when(() -> UserEntityMapper.toDomain(entity2)).thenReturn(usuario2);

            PageOutput<Usuario> result = usuarioRepository.listarTodosAtivos(pageable);

            assertEquals(5, result.totalPages());
            assertEquals(10, result.totalElements());
        }
    }
}