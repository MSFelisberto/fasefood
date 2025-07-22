package br.com.fiap.fasefood.core.usecase.interfaces.cardapio;

import br.com.fiap.fasefood.core.domain.entities.Cardapio;
import br.com.fiap.fasefood.infra.controller.dto.CreateCardapioDTO;

public interface CriarCardapioUseCase {
    Cardapio criarCardapio(CreateCardapioDTO createCardapioDTO);
}