package br.com.fiap.fasefood.infra.controllers.dto.cardapio;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ItensCreateCardapioItemDTO(
        @NotBlank String nome,
        @NotBlank String descricao,
        @NotNull @DecimalMin("0.01") BigDecimal preco,
        @NotNull Boolean apenasNoLocal,
        @NotBlank String caminhoFoto
) {}