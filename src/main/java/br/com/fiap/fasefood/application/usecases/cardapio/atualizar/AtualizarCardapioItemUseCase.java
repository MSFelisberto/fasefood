package br.com.fiap.fasefood.application.usecases.cardapio.atualizar;

import br.com.fiap.fasefood.infra.controllers.dto.cardapio.UpdateCardapioItemDTO;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CardapioItemResponseDTO;

public interface AtualizarCardapioItemUseCase {
    CardapioItemResponseDTO atualizar(Long id, UpdateCardapioItemDTO dto);
}