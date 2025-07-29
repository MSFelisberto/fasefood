package br.com.fiap.fasefood.infra.controller.dto.cardapio;

public record CardapioResponseDTO(
        Long id,
        Long restauranteId,
        String nome,
        String descricao
) {}