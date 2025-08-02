package br.com.fiap.fasefood.infra.controllers.dto.usuario;

import jakarta.validation.constraints.NotNull;

public record UpdateUserTypeDTO(
        @NotNull(message = "O ID do tipo de usuário é obrigatório")
        Long tipoUsuarioId
) {
}
