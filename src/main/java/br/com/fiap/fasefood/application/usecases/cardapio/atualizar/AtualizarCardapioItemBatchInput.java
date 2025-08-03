package br.com.fiap.fasefood.application.usecases.cardapio.atualizar;

import java.math.BigDecimal;

public record AtualizarCardapioItemBatchInput(
        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        Boolean apenasNoLocal,
        String caminhoFoto
) {
}
