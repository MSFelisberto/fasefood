package br.com.fiap.fasefood.application.usecases.cardapio.listar;

public record CardapioResponseOutput(
        Long id,
        Long restauranteId,
        String nome,
        String descricao
) {
}
