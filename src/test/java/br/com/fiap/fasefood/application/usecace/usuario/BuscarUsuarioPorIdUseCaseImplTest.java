package br.com.fiap.fasefood.application.usecace.usuario;

import br.com.fiap.fasefood.application.usecase.usuario.BuscarUsuarioPorIdUseCaseImpl;
import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.UsuarioRepository;
import br.com.fiap.fasefood.infra.controller.dto.ListUserDTO;
import br.com.fiap.fasefood.infra.controller.mapper.UsuarioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BuscarUsuarioPorIdUseCaseImplTest {

    private BuscarUsuarioPorIdUseCaseImpl buscarUsuarioPorIdUseCase;
    private UsuarioRepository usuarioRepository;

    private static final Long USUARIO_ID = 1L;
    @BeforeEach
    public void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        buscarUsuarioPorIdUseCase = new BuscarUsuarioPorIdUseCaseImpl(usuarioRepository);
    }

    @Test
    public void deveRetornarUsuarioComSucesso(){

        try(MockedStatic<UsuarioMapper> usuarioMapper =  mockStatic(UsuarioMapper.class)) {
            Usuario usuario = mock(Usuario.class);
            ListUserDTO listUserDTO = mock(ListUserDTO.class);

            when(usuarioRepository.findById(USUARIO_ID)).thenReturn(Optional.of(usuario));
            usuarioMapper.when(() -> UsuarioMapper.toListUserDTO(usuario)).thenReturn(listUserDTO);

            ListUserDTO response = buscarUsuarioPorIdUseCase.buscarPorId(USUARIO_ID);

            assertEquals(response, listUserDTO);
            verify(usuarioRepository,times(1)).findById(USUARIO_ID);

        }
    }

    @Test
    public void deveLancarExcecaoAoNaoEncontrarUsuario(){
        when(usuarioRepository.findById(USUARIO_ID)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, ()-> buscarUsuarioPorIdUseCase.buscarPorId(USUARIO_ID));
        assertEquals("Usuário não encontrado com ID: 1", exception.getMessage());

    }
}
