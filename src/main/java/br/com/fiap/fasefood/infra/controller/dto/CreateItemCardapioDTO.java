package br.com.fiap.fasefood.infra.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateItemCardapioDTO(
        @NotBlank(message = "O nome é obrigatório")
        String nome,
        @NotBlank(message = "A descrição é obrigatória")
        String descricao,
        @NotBlank(message = "O preço é obrigatória")
        double preco,
        @NotBlank(message = "A disponilidade apenas para pedir no restaurante é obrigatória")
        Boolean flagDisponibilidade,
        @NotBlank(message = "A foto é obrigatória")
        String fotoPath
) {
}
