package br.com.fiap.fasefood.application.usecases.cardapio.criar;

import java.util.List;

public record CriarCardapioItemsBatchInput(
        Long cardapioId,
        List<ItemCardapioInput> itens
) {
}
