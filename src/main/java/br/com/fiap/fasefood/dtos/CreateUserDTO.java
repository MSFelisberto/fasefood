package br.com.fiap.fasefood.dtos;

import br.com.fiap.fasefood.entities.Endereco;
import br.com.fiap.fasefood.enums.ETipoUsuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserDTO(
        @NotBlank
        String nome,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String login,
        @NotBlank
        String senha,
        @NotNull
        ETipoUsuario tipoUsuario,
        @NotNull
        @Valid
        EnderecoDTO endereco
) {
}
