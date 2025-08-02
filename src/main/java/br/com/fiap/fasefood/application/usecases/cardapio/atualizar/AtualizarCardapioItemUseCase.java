package br.com.fiap.fasefood.application.usecases.cardapio.atualizar;

import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioItemOutput;

public interface AtualizarCardapioItemUseCase {
    CriarCardapioItemOutput atualizar(Long id, AtualizarCardapioItemInput input);
}