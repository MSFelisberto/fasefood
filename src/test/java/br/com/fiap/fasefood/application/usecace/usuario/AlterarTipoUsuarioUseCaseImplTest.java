package br.com.fiap.fasefood.application.usecace.usuario;

import br.com.fiap.fasefood.application.usecase.usuario.AlterarTipoUsuarioUseCaseImpl;
import br.com.fiap.fasefood.core.domain.entities.TipoUsuario;
import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.TipoUsuarioRepository;
import br.com.fiap.fasefood.core.usecase.gateways.UsuarioRepository;
import br.com.fiap.fasefood.infra.controller.dto.ListUserDTO;
import br.com.fiap.fasefood.infra.controller.dto.UpdateUserTypeDTO;
import br.com.fiap.fasefood.infra.controller.mapper.UsuarioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AlterarTipoUsuarioUseCaseImplTest {

    private AlterarTipoUsuarioUseCaseImpl alterarTipoUsuarioUseCase;
    private UsuarioRepository usuarioRepository;
    private TipoUsuarioRepository tipoUsuarioRepository;

    private UpdateUserTypeDTO updateUserTypeDTO;
    private Usuario usuario;
    private static final Long USUARIO_ID = 1L;

    @BeforeEach
    public void setUp() {

        usuarioRepository = mock(UsuarioRepository.class);
        tipoUsuarioRepository = mock(TipoUsuarioRepository.class);
        alterarTipoUsuarioUseCase = new AlterarTipoUsuarioUseCaseImpl(usuarioRepository,tipoUsuarioRepository);

        updateUserTypeDTO = mock(UpdateUserTypeDTO.class);
        usuario = mock(Usuario.class);
    }

    @Test
    public void deveAlterarTipoUsuarioComSucesso() {
        try (MockedStatic<UsuarioMapper> usuarioMapper = mockStatic(UsuarioMapper.class)) {

            ListUserDTO listUserDTO = mock(ListUserDTO.class);
            TipoUsuario novoTipoUsuario = mock(TipoUsuario.class);
            Usuario usuarioAtualizado = mock(Usuario.class);

            when(usuarioRepository.findById(USUARIO_ID)).thenReturn(Optional.of(usuario));
            when(tipoUsuarioRepository.findById(updateUserTypeDTO.tipoUsuarioId())).thenReturn(Optional.of(novoTipoUsuario));
            when(usuarioRepository.salvar(usuario)).thenReturn(usuarioAtualizado);

            usuarioMapper.when(() -> UsuarioMapper.toListUserDTO(usuarioAtualizado)).thenReturn(listUserDTO);

            ListUserDTO response = alterarTipoUsuarioUseCase.alterarTipoUsuario(USUARIO_ID, updateUserTypeDTO);

            verify(usuarioRepository, times(1)).salvar(usuario);
            assertEquals(response, listUserDTO);
        }
    }

    @Test
    public void deveLancarExcecaoQuandoNaoEncontrarUsuario() {

        when(usuarioRepository.findById(USUARIO_ID)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> alterarTipoUsuarioUseCase.alterarTipoUsuario(USUARIO_ID, updateUserTypeDTO));
        assertEquals("Usuário não encontrado com ID: 1", exception.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoNaoEncontrarTipoUsuario() {

        when(usuarioRepository.findById(USUARIO_ID)).thenReturn(Optional.of(usuario));
        when(tipoUsuarioRepository.findById(updateUserTypeDTO.tipoUsuarioId())).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,() -> alterarTipoUsuarioUseCase.alterarTipoUsuario(USUARIO_ID, updateUserTypeDTO));
        assertEquals("Tipo de usuário não encontrado com ID: 0", exception.getMessage());
    }

}
