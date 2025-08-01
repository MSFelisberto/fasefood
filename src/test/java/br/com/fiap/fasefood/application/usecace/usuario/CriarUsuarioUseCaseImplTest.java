package br.com.fiap.fasefood.application.usecace.usuario;

import br.com.fiap.fasefood.application.usecase.usuario.CriarUsuarioUseCaseImpl;
import br.com.fiap.fasefood.core.domain.entities.TipoUsuario;
import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.ResourceAlreadyExists;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.TipoUsuarioRepository;
import br.com.fiap.fasefood.core.usecase.gateways.UsuarioRepository;
import br.com.fiap.fasefood.infra.controller.dto.CreateUserDTO;
import br.com.fiap.fasefood.infra.controller.mapper.UsuarioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CriarUsuarioUseCaseImplTest {

    private CriarUsuarioUseCaseImpl criarUsuarioUseCase;
    private UsuarioRepository usuarioRepository;
    private TipoUsuarioRepository tipoUsuarioRepository;
    private CreateUserDTO createUserDTO;
    private Usuario usuario;


    @BeforeEach
    public void setUp() {

        usuarioRepository = mock(UsuarioRepository.class);
        tipoUsuarioRepository = mock(TipoUsuarioRepository.class);
        criarUsuarioUseCase = new CriarUsuarioUseCaseImpl(usuarioRepository,tipoUsuarioRepository);

        createUserDTO = mock(CreateUserDTO.class);
        usuario = mock(Usuario.class);
    }

    @Test
    public void deveCriarUsuarioComSucesso(){

        try(MockedStatic<UsuarioMapper> usuarioMapper = mockStatic(UsuarioMapper.class)) {
            TipoUsuario tipoUsuario = mock(TipoUsuario.class);
            Usuario usuarioSalvo = mock(Usuario.class);

            when(usuarioRepository.findByEmail(createUserDTO.email())).thenReturn(Optional.empty());
            when(tipoUsuarioRepository.findByNome(createUserDTO.tipoUsuarioNome())).thenReturn(Optional.of(tipoUsuario));

            usuarioMapper.when(() -> UsuarioMapper.toDomain(createUserDTO, tipoUsuario)).thenReturn(usuario);

            when(usuarioRepository.salvar(usuario)).thenReturn(usuarioSalvo);

            Usuario response = criarUsuarioUseCase.criarUsuario(createUserDTO);

            assertEquals(response,usuarioSalvo);
            verify(usuarioRepository,times(1)).salvar(usuario);

        }
    }


    @Test
    public void deveLancarExcecaoQuandoEncontrarUsuario(){

        when(usuarioRepository.findByEmail(createUserDTO.email())).thenReturn(Optional.of(usuario));
        ResourceAlreadyExists exception = assertThrows(ResourceAlreadyExists.class, ()-> criarUsuarioUseCase.criarUsuario(createUserDTO));

        assertEquals("Usuário já cadastrado", exception.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoNaoEncontrarTipoUsuario(){
        when(usuarioRepository.findByEmail(createUserDTO.email())).thenReturn(Optional.empty());
        when(tipoUsuarioRepository.findByNome(createUserDTO.tipoUsuarioNome())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,()-> criarUsuarioUseCase.criarUsuario(createUserDTO));
        assertEquals("Tipo de usuário '" + createUserDTO.tipoUsuarioNome() + "' não encontrado",exception.getMessage());
    }
}
