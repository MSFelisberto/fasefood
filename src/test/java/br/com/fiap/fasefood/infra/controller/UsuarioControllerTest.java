package br.com.fiap.fasefood.infra.controller;

import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.core.usecase.interfaces.AlterarTipoUsuarioUseCase;
import br.com.fiap.fasefood.core.usecase.interfaces.AtualizarUsuarioUseCase;
import br.com.fiap.fasefood.core.usecase.interfaces.BuscarUsuarioPorIdUseCase;
import br.com.fiap.fasefood.core.usecase.interfaces.CriarUsuarioUseCase;
import br.com.fiap.fasefood.core.usecase.interfaces.DeletarUsuarioUseCase;
import br.com.fiap.fasefood.core.usecase.interfaces.ListarTodosUsuariosUseCase;
import br.com.fiap.fasefood.infra.controller.dto.CreateUserDTO;
import br.com.fiap.fasefood.infra.controller.dto.ListUserDTO;
import br.com.fiap.fasefood.infra.controller.dto.UpdateUserDataDTO;
import br.com.fiap.fasefood.infra.controller.dto.UpdateUserTypeDTO;
import br.com.fiap.fasefood.infra.controller.mapper.UsuarioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {

    private UsuarioController usuarioController;

    private CriarUsuarioUseCase criarUsuarioUseCase;
    private AtualizarUsuarioUseCase atualizarUsuarioUseCase;
    private DeletarUsuarioUseCase deletarUsuarioUseCase;
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private ListarTodosUsuariosUseCase listarTodosUsuariosUseCase;
    private AlterarTipoUsuarioUseCase alterarTipoUsuarioUseCase;

    private static final Long USUARIO_ID = 1L;
    private ListUserDTO listUserDTO;
    private CreateUserDTO createUserDTO;

    @BeforeEach
    public void setUp() {

        criarUsuarioUseCase = mock(CriarUsuarioUseCase.class);
        atualizarUsuarioUseCase = mock(AtualizarUsuarioUseCase.class);
        deletarUsuarioUseCase = mock(DeletarUsuarioUseCase.class);
        buscarUsuarioPorIdUseCase = mock(BuscarUsuarioPorIdUseCase.class);
        listarTodosUsuariosUseCase = mock(ListarTodosUsuariosUseCase.class);
        alterarTipoUsuarioUseCase = mock(AlterarTipoUsuarioUseCase.class);
        usuarioController = new UsuarioController(criarUsuarioUseCase,atualizarUsuarioUseCase,deletarUsuarioUseCase,
                buscarUsuarioPorIdUseCase,listarTodosUsuariosUseCase, alterarTipoUsuarioUseCase);

        listUserDTO = mock(ListUserDTO.class);
        createUserDTO = mock(CreateUserDTO.class);
    }

    @Test
    public void deveListarTodosUsuariosComSucesso(){
        Pageable pageable = PageRequest.of(0, 10);
        Page<ListUserDTO> usuarios = new PageImpl<>(List.of(listUserDTO));
        when(listarTodosUsuariosUseCase.listar(pageable)).thenReturn(usuarios);
        ResponseEntity<Page<ListUserDTO>> response = usuarioController.listarTodos(pageable);
        assertEquals(response.getBody(), usuarios);
    }

    @Test
    public void deveBuscarUsuarioPorID(){
        when(buscarUsuarioPorIdUseCase.buscarPorId(USUARIO_ID)).thenReturn(listUserDTO);
        ResponseEntity<ListUserDTO> response = usuarioController.buscarUsuarioPorId(USUARIO_ID);
        assertEquals(response.getBody(), listUserDTO);
        verify(buscarUsuarioPorIdUseCase, times(1)).buscarPorId(USUARIO_ID);
    }

    @Test
    public void deveSalvarUsuarioComSucesso(){
        try(MockedStatic<UsuarioMapper> usuarioMapper = mockStatic(UsuarioMapper.class)) {

            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("http://localhost");
            Usuario usario = mock(Usuario.class);

            when(criarUsuarioUseCase.criarUsuario(createUserDTO)).thenReturn(usario);
            usuarioMapper.when(() -> UsuarioMapper.toListUserDTO(usario)).thenReturn(listUserDTO);
            ResponseEntity<ListUserDTO> response = usuarioController.saveUser(createUserDTO, uriBuilder);

            assertEquals(response.getBody(), listUserDTO);
            verify(criarUsuarioUseCase, times(1)).criarUsuario(createUserDTO);
        }
    }

    @Test
    public void deveAtualizarUsuarioComSucesso(){

        UpdateUserDataDTO updateUserDataDTO = mock(UpdateUserDataDTO.class);
        when(atualizarUsuarioUseCase.atualizar(USUARIO_ID, updateUserDataDTO)).thenReturn(listUserDTO);
        ResponseEntity<ListUserDTO> response = usuarioController.atualizarUsuario(USUARIO_ID, updateUserDataDTO);

        assertEquals(response.getBody(), listUserDTO);
        verify(atualizarUsuarioUseCase, times(1)).atualizar(USUARIO_ID, updateUserDataDTO);
    }

    @Test
    public void deveDeletarUsuarioComSucesso(){
        doNothing().when(deletarUsuarioUseCase).deletar(USUARIO_ID);
        usuarioController.deletarUsuario(USUARIO_ID);
        verify(deletarUsuarioUseCase,times(1)).deletar(USUARIO_ID);
    }

    @Test
    public void deveAlterarTipoUsuarioComSucesso(){
        UpdateUserTypeDTO updateUserTypeDTO = mock(UpdateUserTypeDTO.class);
        when(alterarTipoUsuarioUseCase.alterarTipoUsuario(USUARIO_ID, updateUserTypeDTO)).thenReturn(listUserDTO);
        ResponseEntity<ListUserDTO>  response = usuarioController.alterarTipoUsuario(USUARIO_ID,updateUserTypeDTO);
        assertEquals(response.getBody(), listUserDTO);
        verify(alterarTipoUsuarioUseCase, times(1)).alterarTipoUsuario(USUARIO_ID, updateUserTypeDTO);
    }
}
