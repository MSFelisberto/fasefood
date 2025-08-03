package br.com.fiap.fasefood.application.usecases.cardapio.deletar;

import br.com.fiap.fasefood.core.gateways.CardapioItemRepository;

public class RemoverCardapioItemUseCaseImpl implements RemoverCardapioItemUseCase {

    private final CardapioItemRepository cardapioItemRepository;

    public RemoverCardapioItemUseCaseImpl(CardapioItemRepository cardapioItemRepository) {
        this.cardapioItemRepository = cardapioItemRepository;
    }

    @Override
    public void remover(Long id) {
        cardapioItemRepository.remover(id);
    }
}