package br.com.fiap.fasefood.application.usecases.cardapio.criar;

import java.math.BigDecimal;

public record CriarCardapioItemOutput(
        Long id,
        Long cardapioId,
        String nome,
        String descricao,
        BigDecimal preco,
        boolean apenasNoLocal,
        String caminhoFoto
) {
}
