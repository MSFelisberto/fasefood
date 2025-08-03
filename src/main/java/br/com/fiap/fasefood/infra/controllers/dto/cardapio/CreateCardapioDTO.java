package br.com.fiap.fasefood.infra.controllers.dto.cardapio;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CreateCardapioDTO(
        @NotNull Long restauranteId,
        @NotBlank String nome,
        String descricao,
        @Valid List<ItensCreateCardapioItemDTO> itens
) {}