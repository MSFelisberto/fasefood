package br.com.fiap.fasefood.core.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class UsuarioTest {


    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        usuario = Usuario.criarUsuario(1L, "Nome", "email@email", "login", "senha", LocalDate.now(),mock(Endereco.class), new TipoUsuario(1L, "Dono"), true);
    }
    @Test
    public void deveAtualizarCardapio(){
        usuario.atualizarInformacoes("Teste nome", "Teste email");
        assertNotEquals("Nome", usuario.getNome());
        assertNotEquals("Descrição", usuario.getEmail());
    }

    @Test
    public void deveAlterarSenha(){
        usuario.alterarSenha("Nova senha");
        assertNotEquals("senha",usuario.getSenha());
    }

    @Test
    public void deveAlterarTipoUsuario(){
        TipoUsuario tipoUsuario = new TipoUsuario(2L , "Cliente");
        usuario.alterarTipoUsuario(tipoUsuario);
        assertEquals(tipoUsuario, usuario.getTipoUsuario());
    }

    @Test
    public void deveDesativarUsuario(){
        usuario.desativar();
        assertFalse(usuario.isAtivo());
    }
}
