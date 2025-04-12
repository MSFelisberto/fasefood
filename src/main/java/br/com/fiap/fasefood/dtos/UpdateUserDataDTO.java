package br.com.fiap.fasefood.dtos;

import br.com.fiap.fasefood.entities.User;
import jakarta.validation.constraints.NotNull;

public record UpdateUserDataDTO(
        @NotNull
        Long id,
        String nome,
        String email,
        EnderecoDTO endereco
) {
}
