package br.com.fiap.fasefood.application.usecases.usuario.mappers;

import br.com.fiap.fasefood.application.usecases.usuario.UsuarioOutput;
import br.com.fiap.fasefood.application.usecases.usuario.listar.ListUserOutput;
import br.com.fiap.fasefood.core.entities.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class UsuarioOutputMapperTest {

    @Test
    public void deveRetornarUsuarioResponseOutPut(){
        Usuario usuario = mock(Usuario.class);
        ListUserOutput output = UsuarioOutputMapper.toOutput(usuario);
        assertNotNull(output);
    }

    @Test
    public void deveRetornarNuloToOutPut(){
        ListUserOutput output = UsuarioOutputMapper.toOutput(null);
        assertNull(output);
    }
    @Test
    public void deveRetornarUsuarioResponseToUsuarioOutput(){
        Usuario usuario = mock(Usuario.class);
        UsuarioOutput output = UsuarioOutputMapper.toUsuarioOutput(usuario);
        assertNotNull(output);
    }
    @Test
    public void deveRetornarNuloToOutPutToUsuarioOutput(){
        UsuarioOutput output = UsuarioOutputMapper.toUsuarioOutput(null);
        assertNull(output);
    }
}
