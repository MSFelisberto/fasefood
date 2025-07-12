package br.com.fiap.fasefood.infra.controller.dto;

public record UsuarioResponseDTO(
    Long id,
    String nome,
    String email,
    String login
) {}