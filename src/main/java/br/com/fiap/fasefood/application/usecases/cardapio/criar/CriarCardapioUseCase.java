package br.com.fiap.fasefood.application.usecases.cardapio.criar;

import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CardapioResponseDTO;

public interface CriarCardapioUseCase {
    CardapioResponseDTO criar(CriarCardapioInput criarCardapioInput);
}