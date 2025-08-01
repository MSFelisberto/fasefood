package br.com.fiap.fasefood.application.usecace.usuario;

import br.com.fiap.fasefood.application.usecase.usuario.ListarTodosUsuariosUseCaseImpl;
import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.core.usecase.gateways.UsuarioRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.fiap.fasefood.infra.controller.dto.ListUserDTO;
import br.com.fiap.fasefood.infra.controller.mapper.UsuarioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.mockito.Mockito.mock;

public class ListarTodosUsuariosUseCaseImplTest {

    private ListarTodosUsuariosUseCaseImpl listarTodosUsuariosUseCase;
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    public void setUp() {

        usuarioRepository = mock(UsuarioRepository.class);
        listarTodosUsuariosUseCase = new ListarTodosUsuariosUseCaseImpl(usuarioRepository);
    }

    @Test
    public void deveListarUsuarioPaginadoComSucesso(){
        try(MockedStatic<UsuarioMapper> usuarioMapper = mockStatic(UsuarioMapper.class)) {

            Pageable pageable = PageRequest.of(0, 10);
            Usuario usuario = mock(Usuario.class);
            Page<Usuario> page = new PageImpl<>(List.of(usuario));
            ListUserDTO listUserDTO = mock(ListUserDTO.class);

            when(usuarioRepository.listarTodosAtivos(pageable)).thenReturn(page);
            usuarioMapper.when(()-> UsuarioMapper.toListUserDTO(usuario)).thenReturn(listUserDTO);

           Page<ListUserDTO> response = listarTodosUsuariosUseCase.listar(pageable);

            assertNotNull(response);
            assertEquals(1, response.getTotalElements());
        }
    }
}
