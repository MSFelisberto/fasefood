package br.com.fiap.fasefood.core.usecase.interfaces.cardapio;

import br.com.fiap.fasefood.infra.controller.dto.cardapio.CardapioItemResponseDTO;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.UpdateCardapioItemsBatchDTO;

import java.util.List;

public interface AtualizarCardapioItensBatchUseCase {
    List<CardapioItemResponseDTO> atualizarEmLote(UpdateCardapioItemsBatchDTO dto);
}