package br.com.fiap.fasefood.infra.controller.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;

public record UpdateUserDataDTO(
        String nome,

        @Email(message = "Formato de email inválido")
        String email,

        @Valid
        EnderecoDTO endereco
) {
}
