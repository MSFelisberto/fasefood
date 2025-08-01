package br.com.fiap.fasefood.application.usecases.cardapio.mappers;

import br.com.fiap.fasefood.application.usecases.cardapio.listar.CardapioResponseOutput;
import br.com.fiap.fasefood.core.entities.Cardapio;

public class CardapioOutputMapper {
    public static CardapioResponseOutput toCardapioResponseOutput(Cardapio cardapio) {
        return new CardapioResponseOutput(
                cardapio.getId(),
                cardapio.getRestaurante().getId(),
                cardapio.getNome(),
                cardapio.getDescricao()
        );
    }
}
