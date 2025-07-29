package br.com.fiap.fasefood.core.usecase.interfaces.cardapio;

import br.com.fiap.fasefood.infra.controller.dto.cardapio.CardapioItemResponseDTO;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CreateCardapioItemsBatchDTO;

import java.util.List;

public interface CriarCardapioItemsBatchUseCase {
    List<CardapioItemResponseDTO> criarEmLote(CreateCardapioItemsBatchDTO dto);
}