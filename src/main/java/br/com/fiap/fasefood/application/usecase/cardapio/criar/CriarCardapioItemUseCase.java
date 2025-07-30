package br.com.fiap.fasefood.application.usecase.cardapio.criar;

import br.com.fiap.fasefood.infra.controller.dto.cardapio.CreateCardapioItemDTO;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CardapioItemResponseDTO;

public interface CriarCardapioItemUseCase {
    CardapioItemResponseDTO criar(CreateCardapioItemDTO dto);
}