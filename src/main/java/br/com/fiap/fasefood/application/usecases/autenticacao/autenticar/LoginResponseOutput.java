package br.com.fiap.fasefood.application.usecases.autenticacao.autenticar;

public record LoginResponseOutput(
        boolean sucesso,
        String mensagem
) {
}
