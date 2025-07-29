package br.com.fiap.fasefood.infra.controller.dto.cardapio;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CreateCardapioItemsBatchDTO(
        @NotNull(message = "O ID do cardápio é obrigatório")
        Long cardapioId,

        @NotEmpty(message = "A lista de itens não pode ser vazia")
        @Valid
        List<ItensCreateCardapioItemDTO> itens
) {}