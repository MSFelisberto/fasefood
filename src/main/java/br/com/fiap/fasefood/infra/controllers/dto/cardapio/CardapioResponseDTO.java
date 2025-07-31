package br.com.fiap.fasefood.infra.controllers.dto.cardapio;

public record CardapioResponseDTO(
        Long id,
        Long restauranteId,
        String nome,
        String descricao
) {}