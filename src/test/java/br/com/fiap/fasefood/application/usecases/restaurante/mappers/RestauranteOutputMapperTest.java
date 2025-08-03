package br.com.fiap.fasefood.application.usecases.restaurante.mappers;

import br.com.fiap.fasefood.application.usecases.restaurante.RestauranteOutput;
import br.com.fiap.fasefood.core.entities.Restaurante;
import br.com.fiap.fasefood.core.entities.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RestauranteOutputMapperTest {

    @Test
    public void deveRetornarRestauranteResponseOutPut(){
        Restaurante restaurante = mock(Restaurante.class);
        when(restaurante.getDono()).thenReturn(mock(Usuario.class));
        RestauranteOutput output = RestauranteOutputMapper.toRestauranteOutput(restaurante);
        assertNotNull(output);
    }
}
