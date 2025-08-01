package br.com.fiap.fasefood.application.usecases.cardapio.criar;

import java.util.List;

public record CriarCardapioInput(
        Long restauranteId,
        String nome,
        String descricao,
        List<CriarCardapioItemInput> itens
) {
}
