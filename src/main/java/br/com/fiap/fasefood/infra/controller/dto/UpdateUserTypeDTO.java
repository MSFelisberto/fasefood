package br.com.fiap.fasefood.infra.controller.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateUserTypeDTO(
        @NotNull(message = "O ID do tipo de usuário é obrigatório")
        Long tipoUsuarioId
) {
}
