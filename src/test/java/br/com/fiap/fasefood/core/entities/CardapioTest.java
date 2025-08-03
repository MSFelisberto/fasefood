package br.com.fiap.fasefood.core.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;


@ExtendWith(MockitoExtension.class)
public class CardapioTest {

    private Cardapio cardapio;

    @BeforeEach
    public void setUp() {
        cardapio = Cardapio.create(1L, mock(Restaurante.class), "Nome", "Descrição", true);
    }
    @Test
    public void deveAtualizarCardapio(){
        cardapio.atualizar("Teste Atualização", "Teste Descrição");
        assertNotEquals("Nome", cardapio.getNome());
        assertNotEquals("Descrição", cardapio.getDescricao());

    }

    @Test
    public void deveDesativarCardapio(){
        cardapio.desativar();
        assertFalse(cardapio.isAtivo());
    }
}
