package br.com.fiap.fasefood.application.usecases.cardapio.criar;

import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CardapioResponseDTO;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CreateCardapioDTO;

public interface CriarCardapioUseCase {
    CardapioResponseDTO criar(CreateCardapioDTO dto);
}