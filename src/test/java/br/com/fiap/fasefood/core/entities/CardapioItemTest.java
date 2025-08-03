package br.com.fiap.fasefood.core.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class CardapioItemTest {
    private CardapioItem cardapioItem;

    @BeforeEach
    public void setUp() {
        cardapioItem = CardapioItem.create(1L, mock(Cardapio.class), "Nome", "Descrição", BigDecimal.valueOf(10),true, "Path", true);
    }

    @Test
    public void deveAtualizarItemCardapio(){
        cardapioItem.atualizar("Teste Nome", "Teste Descrição", BigDecimal.valueOf(15), false, "Outro caminho");
        assertNotEquals("Nome", cardapioItem.getNome());
        assertNotEquals("Descrição", cardapioItem.getDescricao());
        assertNotEquals(BigDecimal.valueOf(10), cardapioItem.getPreco());
        assertFalse(cardapioItem.isApenasNoLocal());
        assertNotEquals("Path", cardapioItem.getCaminhoFoto());

    }

    @Test
    public void deveDesativarItem(){
        cardapioItem.desativar();
        assertFalse(cardapioItem.isAtivo());
    }

}
