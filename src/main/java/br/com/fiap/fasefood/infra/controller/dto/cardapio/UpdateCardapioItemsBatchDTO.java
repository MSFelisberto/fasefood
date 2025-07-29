package br.com.fiap.fasefood.infra.controller.dto.cardapio;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record UpdateCardapioItemsBatchDTO(
        @NotEmpty(message = "A lista de itens para atualização não pode ser vazia")
        @Valid
        List<UpdateCardapioItemRequestDTO> itens
) {}