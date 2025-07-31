package br.com.fiap.fasefood.infra.controllers.dto;

public record UsuarioRequestDTO(
    String nome,
    String email,
    String login,
    String senha
) {}