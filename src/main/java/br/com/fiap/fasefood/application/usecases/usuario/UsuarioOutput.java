package br.com.fiap.fasefood.application.usecases.usuario;

public record UsuarioOutput(
        Long id,
        String nome,
        String email,
        String login
) {
}
