package br.com.fiap.fasefood.infra.controllers.dto.usuario;

public record UsuarioResponseDTO(
    Long id,
    String nome,
    String email,
    String login
) {}