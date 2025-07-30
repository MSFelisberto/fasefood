package br.com.fiap.fasefood.application.usecase.cardapio.criar;

import br.com.fiap.fasefood.infra.controller.dto.cardapio.CardapioResponseDTO;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CreateCardapioDTO;

public interface CriarCardapioUseCase {
    CardapioResponseDTO criar(CreateCardapioDTO dto);
}