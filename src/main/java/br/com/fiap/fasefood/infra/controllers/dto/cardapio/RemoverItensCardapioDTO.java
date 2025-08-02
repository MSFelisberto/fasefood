package br.com.fiap.fasefood.infra.controllers.dto.cardapio;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record RemoverItensCardapioDTO(
        @NotEmpty(message = "A lista de IDs de itens não pode ser vazia.")
        List<Long> itemIds
) {
}
