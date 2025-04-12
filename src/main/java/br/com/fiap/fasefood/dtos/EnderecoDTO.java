package br.com.fiap.fasefood.dtos;

import br.com.fiap.fasefood.entities.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record EnderecoDTO(
        @NotBlank
        String logradouro,
        @NotBlank
        String numero,
        @NotBlank
        @Pattern(regexp = "\\d{8}")
        String cep,
        String complemento,
        @NotBlank
        String bairro,
        @NotBlank
        String cidade,
        @NotBlank
        String uf
) {
}
