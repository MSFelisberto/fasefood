package br.com.fiap.fasefood.core.usecase.interfaces.cardapio;


import br.com.fiap.fasefood.infra.controller.dto.ListCardapioDTO;
import br.com.fiap.fasefood.infra.controller.dto.UpdateCardapioDTO;

public interface AtualizarCardapioUseCase {
    ListCardapioDTO atualizar(Long id, UpdateCardapioDTO updateUserDTO);
}
