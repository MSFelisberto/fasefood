package br.com.fiap.fasefood.application.usecases.cardapio.deletar;

import br.com.fiap.fasefood.core.gateways.CardapioItemRepository;

public class DeletarCardapioItemUseCaseImpl implements DeletarCardapioItemUseCase {

    private final CardapioItemRepository cardapioItemRepository;

    public DeletarCardapioItemUseCaseImpl(CardapioItemRepository cardapioItemRepository) {
        this.cardapioItemRepository = cardapioItemRepository;
    }

    @Override
    public void deletar(Long id) {
        cardapioItemRepository.deletar(id);
    }
}