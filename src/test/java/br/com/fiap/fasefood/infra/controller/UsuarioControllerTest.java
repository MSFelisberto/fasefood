package br.com.fiap.fasefood.infra.controller;

import br.com.fiap.fasefood.application.usecases.shared.paginacao.PageOutput;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PaginationInput;
import br.com.fiap.fasefood.application.usecases.usuario.alterar.AlterarTipoUsuarioUseCase;
import br.com.fiap.fasefood.application.usecases.usuario.alterar.AtualizarUsuarioUseCase;
import br.com.fiap.fasefood.application.usecases.usuario.alterar.UpdateUserDataInput;
import br.com.fiap.fasefood.application.usecases.usuario.alterar.UpdateUserTypeInput;
import br.com.fiap.fasefood.application.usecases.usuario.criar.CriarUsuarioInput;
import br.com.fiap.fasefood.application.usecases.usuario.criar.CriarUsuarioOutput;
import br.com.fiap.fasefood.application.usecases.usuario.criar.CriarUsuarioUseCase;
import br.com.fiap.fasefood.application.usecases.usuario.deletar.DeletarUsuarioUseCase;
import br.com.fiap.fasefood.application.usecases.usuario.listar.BuscarUsuarioPorIdUseCase;
import br.com.fiap.fasefood.application.usecases.usuario.listar.ListUserOutput;
import br.com.fiap.fasefood.application.usecases.usuario.listar.ListarTodosUsuariosUseCase;
import br.com.fiap.fasefood.infra.controllers.UsuarioController;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CardapioResponseDTO;
import br.com.fiap.fasefood.infra.controllers.dto.usuario.CreateUserDTO;
import br.com.fiap.fasefood.infra.controllers.dto.usuario.ListUserDTO;
import br.com.fiap.fasefood.infra.controllers.dto.usuario.UpdateUserDataDTO;
import br.com.fiap.fasefood.infra.controllers.dto.usuario.UpdateUserTypeDTO;
import br.com.fiap.fasefood.infra.controllers.mapper.usuario.ListUserMapper;
import br.com.fiap.fasefood.infra.controllers.mapper.usuario.UsuarioMapper;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
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
    private ListUserOutput listUserOutput;
    private CreateUserDTO createUserDTO;
    private ListUserDTO listUserDTO;

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

        listUserOutput = mock(ListUserOutput.class);
        createUserDTO = mock(CreateUserDTO.class);
        listUserDTO = mock(ListUserDTO.class);
    }

    @Test
    public void deveListarTodosUsuariosComSucesso(){
        try(MockedStatic<UsuarioMapper> cardapioMapper = mockStatic(UsuarioMapper.class)) {

            Pageable pageable = PageRequest.of(0, 10);
            PaginationInput paginationInput = new PaginationInput(0, 10, "nome", "asc");
            PageOutput<ListUserOutput> pageOutput =  new PageOutput<>(List.of(listUserOutput), 0, 10, 1 , 1L);
            CardapioResponseDTO cardapioResponseDTO = new CardapioResponseDTO(1L, 1L, "Nome", "Descrição");
            Page<CardapioResponseDTO> responseDTOPage = new PageImpl<>(List.of(cardapioResponseDTO));

            cardapioMapper.when(()-> UsuarioMapper.toPaginationInput(pageable)).thenReturn(paginationInput);
            when(listarTodosUsuariosUseCase.listar(paginationInput)).thenReturn(pageOutput);
            cardapioMapper.when(()-> UsuarioMapper.toListUserOutputPaginacao(pageOutput)).thenReturn(responseDTOPage);

            ResponseEntity<Page<ListUserDTO>> result = usuarioController.listarTodos(pageable);

            assertNotNull(result);
            assertEquals(1, Objects.requireNonNull(result.getBody()).getTotalElements());
        }
    }
    
    @Test
    public void deveBuscarUsuarioPorID(){
        try(MockedStatic<ListUserMapper> mapperMock = mockStatic(ListUserMapper.class)) {
            when(buscarUsuarioPorIdUseCase.buscarPorId(USUARIO_ID)).thenReturn(listUserOutput);
            mapperMock.when(()-> ListUserMapper.toDTO(listUserOutput)).thenReturn(listUserDTO);
            ResponseEntity<ListUserDTO> response = usuarioController.buscarUsuarioPorId(USUARIO_ID);
            assertEquals(response.getBody(), listUserDTO);
            verify(buscarUsuarioPorIdUseCase, times(1)).buscarPorId(USUARIO_ID);
        }
    }

    @Test
    public void deveSalvarUsuarioComSucesso(){
        try(MockedStatic<UsuarioMapper> usuarioMapper = mockStatic(UsuarioMapper.class)) {

            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("http://localhost");
            CriarUsuarioInput criarUsuarioInput = mock(CriarUsuarioInput.class);
            CriarUsuarioOutput criarUsuarioOutput = mock(CriarUsuarioOutput.class);
            ListUserDTO listUserDTO = mock(ListUserDTO.class);

            usuarioMapper.when(()-> UsuarioMapper.toCriarUsuarioInput(createUserDTO)).thenReturn(criarUsuarioInput);
            when(criarUsuarioUseCase.criarUsuario(criarUsuarioInput)).thenReturn(criarUsuarioOutput);
            usuarioMapper.when(() -> UsuarioMapper.toListUserDTO(criarUsuarioOutput)).thenReturn(listUserDTO);
            ResponseEntity<ListUserDTO> response = usuarioController.saveUser(createUserDTO, uriBuilder);

            assertNotNull(response.getBody());
            verify(criarUsuarioUseCase, times(1)).criarUsuario(criarUsuarioInput);
        }
    }

    @Test
    public void deveAtualizarUsuarioComSucesso(){
        try(MockedStatic<UsuarioMapper> usuarioMapper = mockStatic(UsuarioMapper.class);
            MockedStatic<ListUserMapper> listUserMapper = mockStatic(ListUserMapper.class)) {

            UpdateUserDataDTO updateUserDataDTO = mock(UpdateUserDataDTO.class);
            UpdateUserDataInput updateUserDataInput = mock(UpdateUserDataInput.class);

            usuarioMapper.when(()-> UsuarioMapper.toUpdateUserDataInput(updateUserDataDTO)).thenReturn(updateUserDataInput);
            when(atualizarUsuarioUseCase.atualizar(USUARIO_ID, updateUserDataInput)).thenReturn(listUserOutput);
            listUserMapper.when(()-> ListUserMapper.toDTO(listUserOutput)).thenReturn(listUserDTO);

            ResponseEntity<ListUserDTO> response = usuarioController.atualizarUsuario(USUARIO_ID, updateUserDataDTO);

            assertEquals(response.getBody(), listUserDTO);
            verify(atualizarUsuarioUseCase, times(1)).atualizar(USUARIO_ID, updateUserDataInput);
        }
    }

    @Test
    public void deveDeletarUsuarioComSucesso(){
        doNothing().when(deletarUsuarioUseCase).deletar(USUARIO_ID);
        usuarioController.deletarUsuario(USUARIO_ID);
        verify(deletarUsuarioUseCase,times(1)).deletar(USUARIO_ID);
    }

    @Test
    public void deveAlterarTipoUsuarioComSucesso(){
        try(MockedStatic<UsuarioMapper> usuarioMapper = mockStatic(UsuarioMapper.class)) {
            UpdateUserTypeDTO updateUserTypeDTO = mock(UpdateUserTypeDTO.class);
            UpdateUserTypeInput updateUserTypeInput = mock(UpdateUserTypeInput.class);

            usuarioMapper.when(()-> UsuarioMapper.toUpdateUserTypeInput(updateUserTypeDTO)).thenReturn(updateUserTypeInput);
            when(alterarTipoUsuarioUseCase.alterarTipoUsuario(USUARIO_ID, updateUserTypeInput)).thenReturn(listUserOutput);
            usuarioMapper.when(()-> UsuarioMapper.toListUserDTO(listUserOutput)).thenReturn(listUserDTO);

            ResponseEntity<ListUserDTO> response = usuarioController.alterarTipoUsuario(USUARIO_ID, updateUserTypeDTO);

            assertEquals(response.getBody(), listUserDTO);
            verify(alterarTipoUsuarioUseCase, times(1)).alterarTipoUsuario(USUARIO_ID, updateUserTypeInput);
        }
    }
}