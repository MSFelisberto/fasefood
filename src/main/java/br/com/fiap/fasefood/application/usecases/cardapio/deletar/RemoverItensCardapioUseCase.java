package br.com.fiap.fasefood.application.usecases.cardapio.deletar;

import java.util.List;

public interface RemoverItensCardapioUseCase {
    void removerEmLote(List<Long> ids);
}
