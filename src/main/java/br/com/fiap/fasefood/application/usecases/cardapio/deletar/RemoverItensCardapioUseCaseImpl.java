package br.com.fiap.fasefood.application.usecases.cardapio.deletar;

import br.com.fiap.fasefood.core.gateways.CardapioItemRepository;


import java.util.List;

public class RemoverItensCardapioUseCaseImpl implements RemoverItensCardapioUseCase{

    private final CardapioItemRepository cardapioItemRepository;

    public RemoverItensCardapioUseCaseImpl(CardapioItemRepository cardapioItemRepository) {
        this.cardapioItemRepository = cardapioItemRepository;
    }

    @Override
    public void removerEmLote(List<Long> ids) {
        ids.forEach(cardapioItemRepository::remover);
    }
}
