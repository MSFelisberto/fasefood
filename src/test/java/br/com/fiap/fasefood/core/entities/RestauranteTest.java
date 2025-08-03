package br.com.fiap.fasefood.core.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class RestauranteTest {

    private Restaurante restaurante;

    @BeforeEach
    public void setUp() {
        restaurante = Restaurante.create(1L, "Nome",  mock(Endereco.class), "tipoCozinha", LocalTime.now(), LocalTime.now(), mock(Usuario.class), true);
    }
    @Test
    public void atualizarInformacoes(){
        restaurante.atualizarInformacoes("Teste Atualização", "teste tipo cozinha", LocalTime.MIN, LocalTime.MAX);

        assertNotEquals("Nome", restaurante.getNome());
        assertNotEquals("tipoCozinha", restaurante.getTipoCozinha());
        assertEquals(LocalTime.MIN, restaurante.getHorarioAbertura());
        assertEquals(LocalTime.MAX, restaurante.getHorarioFechamento());

    }

    @Test
    public void desativarRestaurante(){
        restaurante.desativar();
        assertFalse(restaurante.isAtivo());
    }


}
