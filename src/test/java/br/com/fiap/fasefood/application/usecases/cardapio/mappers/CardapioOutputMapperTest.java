package br.com.fiap.fasefood.application.usecases.cardapio.mappers;

import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioItemOutput;
import br.com.fiap.fasefood.application.usecases.cardapio.listar.CardapioResponseOutput;
import br.com.fiap.fasefood.core.entities.Cardapio;
import br.com.fiap.fasefood.core.entities.CardapioItem;
import br.com.fiap.fasefood.core.entities.Restaurante;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CardapioOutputMapperTest {

    @Test
    public void deveRetornarCardapioResponseOutPut(){
        Cardapio cardapio = mock(Cardapio.class);
        when(cardapio.getRestaurante()).thenReturn(mock(Restaurante.class));
        CardapioResponseOutput output = CardapioOutputMapper.toCardapioResponseOutput(cardapio);
        assertNotNull(output);
    }

    @Test
    public void deveRetornarCriarCardapioItemOutput(){
        CardapioItem cardapio = mock(CardapioItem.class);
        when(cardapio.getCardapio()).thenReturn(mock(Cardapio.class));
        CriarCardapioItemOutput criarCardapioItemOutput = CardapioOutputMapper.toCriarCardapioItemOutput(cardapio);
        assertNotNull(criarCardapioItemOutput);
    }
    @Test
    public void deveRetornarNulo(){
        CardapioItem cardapioItem = mock(CardapioItem.class);
        CriarCardapioItemOutput criarCardapioItemOutput = CardapioOutputMapper.toCriarCardapioItemOutput(cardapioItem);
        assertNull(criarCardapioItemOutput);
    }
}
