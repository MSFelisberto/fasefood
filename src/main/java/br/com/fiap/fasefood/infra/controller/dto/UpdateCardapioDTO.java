package br.com.fiap.fasefood.infra.controller.dto;

import br.com.fiap.fasefood.core.domain.entities.ItemCardapio;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UpdateCardapioDTO(
        @NotBlank(message = "O nome é obrigatório")
        String nome,

        String descricao,
        List<CreateItemCardapioDTO> itemCardapioDTOList) {
}
