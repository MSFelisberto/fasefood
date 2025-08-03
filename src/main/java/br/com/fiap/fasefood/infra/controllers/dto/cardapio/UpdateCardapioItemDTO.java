package br.com.fiap.fasefood.infra.controllers.dto.cardapio;

import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;

public record UpdateCardapioItemDTO(
        String nome,
        String descricao,
        @DecimalMin("0.01") BigDecimal preco,
        Boolean apenasNoLocal,
        String caminhoFoto
) {}