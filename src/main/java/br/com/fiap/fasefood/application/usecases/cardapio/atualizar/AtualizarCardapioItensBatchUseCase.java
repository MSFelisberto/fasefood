package br.com.fiap.fasefood.application.usecases.cardapio.atualizar;

import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioItemOutput;

import java.util.List;

public interface AtualizarCardapioItensBatchUseCase {
    List<CriarCardapioItemOutput> atualizarEmLote(List<AtualizarCardapioItemBatchInput> inputs);
}