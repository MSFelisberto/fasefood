package br.com.fiap.fasefood.infra.controller.dto;

public record UsuarioRequestDTO(
    String nome,
    String email,
    String login,
    String senha
) {}