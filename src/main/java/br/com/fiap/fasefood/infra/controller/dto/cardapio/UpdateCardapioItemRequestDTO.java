package br.com.fiap.fasefood.infra.controller.dto.cardapio;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateCardapioItemRequestDTO(
        @NotNull(message = "O ID do item é obrigatório")
        Long id,
        String nome,
        String descricao,
        @DecimalMin("0.01") BigDecimal preco,
        Boolean apenasNoLocal,
        String caminhoFoto
) {}