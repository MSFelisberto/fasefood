package br.com.fiap.fasefood.infra.persistence;

import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.infra.controller.mapper.UserEntityMapper;
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
        try(MockedStatic<UserEntityMapper> userEntityMapper = mockStatic(UserEntityMapper.class)) {
            Pageable pageable = PageRequest.of(0, 10);
            Page<UsuarioEntity> entityPage = new PageImpl<>(List.of(usuarioEntity));
            Page<Usuario> page = new PageImpl<>(List.of(usuario));

            when(userJpaRepository.findAllByAtivoTrue(pageable)).thenReturn(entityPage);
            userEntityMapper.when(()-> UserEntityMapper.toDomain(usuarioEntity)).thenReturn(usuario);

            Page<Usuario> response = usuarioRepository.listarTodosAtivos(pageable);

            assertNotNull(response);
            assertEquals(1, response.getTotalElements());
            assertEquals(response, page);
        }
    }
}
