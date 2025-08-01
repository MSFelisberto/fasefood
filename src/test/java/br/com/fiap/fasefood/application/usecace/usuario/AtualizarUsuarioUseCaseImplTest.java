package br.com.fiap.fasefood.application.usecace.usuario;

import br.com.fiap.fasefood.application.usecase.usuario.AtualizarUsuarioUseCaseImpl;
import br.com.fiap.fasefood.core.domain.entities.Endereco;
import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.UsuarioRepository;
import br.com.fiap.fasefood.infra.controller.dto.EnderecoDTO;
import br.com.fiap.fasefood.infra.controller.dto.ListUserDTO;
import br.com.fiap.fasefood.infra.controller.dto.UpdateUserDataDTO;
import br.com.fiap.fasefood.infra.controller.mapper.UsuarioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class AtualizarUsuarioUseCaseImplTest {

    private AtualizarUsuarioUseCaseImpl atualizarUsuarioUseCase;
    private UsuarioRepository usuarioRepository;

    private UpdateUserDataDTO updateUserDataDTO;

    private static final Long USUARIO_ID = 1L;

    @BeforeEach
    public void setUp() {

        usuarioRepository = mock(UsuarioRepository.class);
        atualizarUsuarioUseCase = new AtualizarUsuarioUseCaseImpl(usuarioRepository);

        updateUserDataDTO = mock(UpdateUserDataDTO.class);
    }

    @Test
    public void deveAtualizarUsuarioComSucesso(){
        try(MockedStatic<UsuarioMapper> usuarioMapper = mockStatic(UsuarioMapper.class)) {

            Usuario usuario = mock(Usuario.class);
            Endereco endereco = mock(Endereco.class);
            EnderecoDTO enderecoDTO = mock(EnderecoDTO.class);
            Usuario usuarioSalvo = mock(Usuario.class);
            ListUserDTO listUserDTO = mock(ListUserDTO.class);

            when(usuarioRepository.findById(USUARIO_ID)).thenReturn(Optional.of(usuario));
            when(usuario.getEndereco()).thenReturn(endereco);
            when(updateUserDataDTO.endereco()).thenReturn(enderecoDTO);
            when(usuarioRepository.salvar(usuario)).thenReturn(usuarioSalvo);

            usuarioMapper.when(()-> UsuarioMapper.toListUserDTO(usuarioSalvo)).thenReturn(listUserDTO);

            ListUserDTO response = atualizarUsuarioUseCase.atualizar(USUARIO_ID, updateUserDataDTO);

            assertEquals(response, listUserDTO);
            verify(usuarioRepository, times(1)).salvar(usuario);

        }
    }

    @Test
    public void deveLancarExcecaoUsuarioNaoEncontrado(){

        when(usuarioRepository.findById(USUARIO_ID)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> atualizarUsuarioUseCase.atualizar(USUARIO_ID, updateUserDataDTO));
        assertEquals("Usuário não encontrado com ID: 1", exception.getMessage());
    }

}
