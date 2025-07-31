package br.com.fiap.fasefood.infra.controllers.dto.cardapio;

import java.math.BigDecimal;

public record CardapioItemResponseDTO(
        Long id,
        Long cardapioId,
        String nome,
        String descricao,
        BigDecimal preco,
        boolean apenasNoLocal,
        String caminhoFoto
) {}