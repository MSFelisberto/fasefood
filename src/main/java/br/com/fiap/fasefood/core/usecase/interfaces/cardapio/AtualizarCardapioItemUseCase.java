package br.com.fiap.fasefood.core.usecase.interfaces.cardapio;

import br.com.fiap.fasefood.infra.controller.dto.cardapio.UpdateCardapioItemDTO;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CardapioItemResponseDTO;

public interface AtualizarCardapioItemUseCase {
    CardapioItemResponseDTO atualizar(Long id, UpdateCardapioItemDTO dto);
}