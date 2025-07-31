package br.com.fiap.fasefood.application.usecace.autenticacao;


import br.com.fiap.fasefood.application.usecases.autenticacao.autenticar.AutenticarUsuarioUseCaseImpl;
import br.com.fiap.fasefood.application.usecases.autenticacao.autenticar.LoginResponseOutput;
import br.com.fiap.fasefood.core.entities.Endereco;
import br.com.fiap.fasefood.core.entities.TipoUsuario;
import br.com.fiap.fasefood.core.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.AuthenticationFailedException;
import br.com.fiap.fasefood.core.gateways.UsuarioRepository;
import br.com.fiap.fasefood.infra.controllers.dto.LoginRequestDTO;
import br.com.fiap.fasefood.infra.controllers.dto.LoginResponseDTO;
import br.com.fiap.fasefood.infra.controllers.mapper.autenticar.AutenticarMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AutenticarUsuarioUseCaseImplTest {


    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private AutenticarUsuarioUseCaseImpl autenticarUsuario;

    @BeforeEach
    public void setUp() {
        usuarioRepository = Mockito.mock(UsuarioRepository.class);
        autenticarUsuario = new AutenticarUsuarioUseCaseImpl(usuarioRepository);
    }

    @Test
    public void deveAutenticarComSucesso() {
        LoginRequestDTO request = new LoginRequestDTO("admin", "123");
        Usuario usuario = buildUsuario("admin","123");

        when(usuarioRepository.findByLogin("admin")).thenReturn(Optional.of(usuario));

        LoginResponseOutput response = autenticarUsuario.autenticar(AutenticarMapper.toRequestInput(request));

        assertTrue(response.sucesso());
        assertEquals("Login realizado com sucesso", response.mensagem());
    }

    @Test
    public void deveLancarExcecao_QuandoUsuarioNaoEncontrado() {
        LoginRequestDTO request = new LoginRequestDTO("admin", "123");
        when(usuarioRepository.findByLogin("admin")).thenReturn(Optional.empty());

        AuthenticationFailedException exception = assertThrows(AuthenticationFailedException.class, () -> {
            autenticarUsuario.autenticar(AutenticarMapper.toRequestInput(request));
        });

        assertEquals("Login ou senha incorretos", exception.getMessage());
    }

    @Test
    public void deveLancarExcecao_QuandoSenhaIncorreta() {
        LoginRequestDTO request = new LoginRequestDTO("admin", "senhaErrada");
        Usuario usuario = buildUsuario("admin","senhaCorreta");
        when(usuarioRepository.findByLogin("admin")).thenReturn(Optional.of(usuario));
        AuthenticationFailedException exception = assertThrows(AuthenticationFailedException.class, () -> {
            autenticarUsuario.autenticar(AutenticarMapper.toRequestInput(request));
        });

        assertEquals("Login ou senha incorretos", exception.getMessage());
    }
    private TipoUsuario buildTipoUsuario() {
        return new TipoUsuario(1L, "teste tipo usuario");
    }
    private Usuario buildUsuario(String login , String senha){
        TipoUsuario tipoUsuario = buildTipoUsuario();
        Endereco endereco = buildEndereco();
        Usuario usuario = Usuario.criarUsuario(1L, "teste","teste@teste.com", login,senha, LocalDate.now(),endereco, tipoUsuario, true);

        return usuario;

    }

    private Endereco buildEndereco(){
        return Endereco.criarEndereco(1L, "logradouro", "01", "0000001", "complemento", "bairro","cidade", "uf");
    }
}