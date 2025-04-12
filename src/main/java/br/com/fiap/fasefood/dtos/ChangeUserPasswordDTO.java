package br.com.fiap.fasefood.dtos;

import jakarta.validation.constraints.NotNull;

public record ChangeUserPasswordDTO(
        @NotNull
        Long id,
        String senha) {
}
