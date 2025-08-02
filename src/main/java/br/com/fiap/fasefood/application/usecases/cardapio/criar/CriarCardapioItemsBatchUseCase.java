package br.com.fiap.fasefood.application.usecases.cardapio.criar;

import java.util.List;

public interface CriarCardapioItemsBatchUseCase {
    List<CriarCardapioItemOutput> criarEmLote(CriarCardapioItemsBatchInput dto);
}