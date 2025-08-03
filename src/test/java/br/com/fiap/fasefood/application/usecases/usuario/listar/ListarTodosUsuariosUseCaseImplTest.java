package br.com.fiap.fasefood.application.usecases.usuario.listar;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.fiap.fasefood.application.usecases.shared.paginacao.PageOutput;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PaginationInput;
import br.com.fiap.fasefood.application.usecases.usuario.mappers.UsuarioOutputMapper;
import br.com.fiap.fasefood.core.entities.Usuario;
import br.com.fiap.fasefood.core.gateways.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

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
        try(MockedStatic<UsuarioOutputMapper> usuarioOutputMapper = mockStatic(UsuarioOutputMapper.class)) {

            Usuario usuario = mock(Usuario.class);
            PaginationInput pageable = mock(PaginationInput.class);
            PageOutput<Usuario> page =  new PageOutput<>(List.of(usuario), 0, 10, 1 , 1L);

            ListUserOutput listUserDTO = mock(ListUserOutput.class);

            when(usuarioRepository.listarTodosAtivos(pageable)).thenReturn(page);
            usuarioOutputMapper.when(()-> UsuarioOutputMapper.toOutput(usuario)).thenReturn(listUserDTO);

            PageOutput<ListUserOutput> response = listarTodosUsuariosUseCase.listar(pageable);

            assertNotNull(response);
            assertEquals(10, response.size());
        }
    }
}
