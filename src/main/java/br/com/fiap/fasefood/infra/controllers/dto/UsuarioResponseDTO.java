package br.com.fiap.fasefood.infra.controllers.dto;

public record UsuarioResponseDTO(
    Long id,
    String nome,
    String email,
    String login
) {}