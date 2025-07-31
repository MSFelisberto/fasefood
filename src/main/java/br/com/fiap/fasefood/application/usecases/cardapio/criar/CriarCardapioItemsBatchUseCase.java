package br.com.fiap.fasefood.application.usecases.cardapio.criar;

import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CardapioItemResponseDTO;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CreateCardapioItemsBatchDTO;

import java.util.List;

public interface CriarCardapioItemsBatchUseCase {
    List<CardapioItemResponseDTO> criarEmLote(CreateCardapioItemsBatchDTO dto);
}