package br.com.fiap.fasefood.application.usecases.usuario.criar;

import br.com.fiap.fasefood.application.usecases.shared.endereco.EnderecoInput;
import br.com.fiap.fasefood.core.entities.TipoUsuario;
import br.com.fiap.fasefood.core.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.ResourceAlreadyExists;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.gateways.TipoUsuarioRepository;
import br.com.fiap.fasefood.core.gateways.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CriarUsuarioUseCaseImplTest {

    private CriarUsuarioUseCaseImpl criarUsuarioUseCase;
    private UsuarioRepository usuarioRepository;
    private TipoUsuarioRepository tipoUsuarioRepository;
    private CriarUsuarioInput criarUsuarioInput;
    private Usuario usuario;


    @BeforeEach
    public void setUp() {

        usuarioRepository = mock(UsuarioRepository.class);
        tipoUsuarioRepository = mock(TipoUsuarioRepository.class);
        criarUsuarioUseCase = new CriarUsuarioUseCaseImpl(usuarioRepository,tipoUsuarioRepository);

        criarUsuarioInput = new CriarUsuarioInput("nome", "fiap@fiap.com.br", "fiap", "fiap", "DONO", mock(EnderecoInput.class));
        usuario = mock(Usuario.class);
    }

    @Test
    public void deveCriarUsuarioComSucesso(){

        TipoUsuario tipoUsuario = mock(TipoUsuario.class);

        when(usuarioRepository.findByEmail(criarUsuarioInput.email())).thenReturn(Optional.empty());
        when(tipoUsuarioRepository.findByNome(criarUsuarioInput.tipoUsuarioNome())).thenReturn(Optional.of(tipoUsuario));
        when(usuarioRepository.salvar(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CriarUsuarioOutput response = criarUsuarioUseCase.criarUsuario(criarUsuarioInput);

        assertNotNull(response);
        verify(usuarioRepository,times(1)).salvar(any(Usuario.class));


    }


    @Test
    public void deveLancarExcecaoQuandoEncontrarUsuario(){

        when(usuarioRepository.findByEmail(criarUsuarioInput.email())).thenReturn(Optional.of(usuario));
        ResourceAlreadyExists exception = assertThrows(ResourceAlreadyExists.class, ()-> criarUsuarioUseCase.criarUsuario(criarUsuarioInput));

        assertEquals("Usuário já cadastrado", exception.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoNaoEncontrarTipoUsuario(){
        when(usuarioRepository.findByEmail(criarUsuarioInput.email())).thenReturn(Optional.empty());
        when(tipoUsuarioRepository.findByNome(criarUsuarioInput.tipoUsuarioNome())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,()-> criarUsuarioUseCase.criarUsuario(criarUsuarioInput));
        assertEquals("Tipo de usuário '" + criarUsuarioInput.tipoUsuarioNome() + "' não encontrado",exception.getMessage());
    }
}
