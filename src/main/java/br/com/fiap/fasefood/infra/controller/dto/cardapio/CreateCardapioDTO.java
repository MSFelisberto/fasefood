package br.com.fiap.fasefood.infra.controller.dto.cardapio;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCardapioDTO(
        @NotNull Long restauranteId,
        @NotBlank String nome,
        String descricao
) {}