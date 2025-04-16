package br.com.fiap.fasefood.dtos;

import jakarta.validation.constraints.NotBlank;


public record LoginRequestDTO(

        @NotBlank(message = "O login é obrigatório")
        String login,

        @NotBlank(message = "A senha é obrigatória")
        String senha
) {
}
