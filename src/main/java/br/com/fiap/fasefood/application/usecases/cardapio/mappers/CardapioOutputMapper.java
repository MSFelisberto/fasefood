package br.com.fiap.fasefood.application.usecases.cardapio.mappers;

import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioItemOutput;
import br.com.fiap.fasefood.application.usecases.cardapio.listar.CardapioResponseOutput;
import br.com.fiap.fasefood.core.entities.Cardapio;
import br.com.fiap.fasefood.core.entities.CardapioItem;

public class CardapioOutputMapper {
    public static CardapioResponseOutput toCardapioResponseOutput(Cardapio cardapio) {
        return new CardapioResponseOutput(
                cardapio.getId(),
                cardapio.getRestaurante().getId(),
                cardapio.getNome(),
                cardapio.getDescricao()
        );
    }

    public static CriarCardapioItemOutput toCriarCardapioItemOutput(CardapioItem item) {
        if (item == null || item.getCardapio() == null) {
            return null;
        }
        return new CriarCardapioItemOutput(
                item.getId(),
                item.getCardapio().getId(),
                item.getNome(),
                item.getDescricao(),
                item.getPreco(),
                item.isApenasNoLocal(),
                item.getCaminhoFoto()
        );
    }
}
