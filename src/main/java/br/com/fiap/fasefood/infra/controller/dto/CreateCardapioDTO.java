package br.com.fiap.fasefood.infra.controller.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CreateCardapioDTO(
   @NotBlank(message = "O nome é obrigatório")
    String nome,
   @NotBlank(message = "A descrição é obrigatória")
   String descricao,

   List<CreateItemCardapioDTO> itemCardapioDTO) {}
