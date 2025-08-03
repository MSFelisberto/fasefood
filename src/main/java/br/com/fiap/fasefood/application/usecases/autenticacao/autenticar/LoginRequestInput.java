package br.com.fiap.fasefood.application.usecases.autenticacao.autenticar;

public record LoginRequestInput(
        String login,
        String senha
) {
}
