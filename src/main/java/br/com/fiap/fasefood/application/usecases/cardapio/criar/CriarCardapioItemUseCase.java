package br.com.fiap.fasefood.application.usecases.cardapio.criar;

import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CreateCardapioItemDTO;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CardapioItemResponseDTO;

public interface CriarCardapioItemUseCase {
    CardapioItemResponseDTO criar(CreateCardapioItemDTO dto);
}