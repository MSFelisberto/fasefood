package br.com.fiap.fasefood.application.usecases.cardapio.atualizar;

import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CardapioItemResponseDTO;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.UpdateCardapioItemsBatchDTO;

import java.util.List;

public interface AtualizarCardapioItensBatchUseCase {
    List<CardapioItemResponseDTO> atualizarEmLote(UpdateCardapioItemsBatchDTO dto);
}