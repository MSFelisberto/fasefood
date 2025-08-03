package br.com.fiap.fasefood.application.usecases.cardapio.criar;

import java.math.BigDecimal;

public record ItemCardapioInput (
        String nome,
        String descricao,
        BigDecimal preco,
        Boolean apenasNoLocal,
        String caminhoFoto
) {
}
