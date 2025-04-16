package br.com.fiap.fasefood.dtos;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UpdateUserDataDTO(
        @NotNull(message = "O ID do usuário é obrigatório")
        Long id,

        String nome,

        @Email(message = "Formato de email inválido")
        String email,

        @Valid
        EnderecoDTO endereco
) {
}
