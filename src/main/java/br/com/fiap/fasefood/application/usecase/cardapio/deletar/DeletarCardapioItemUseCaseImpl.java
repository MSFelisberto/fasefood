package br.com.fiap.fasefood.application.usecase.cardapio.deletar;

import br.com.fiap.fasefood.core.usecase.gateways.CardapioItemRepository;

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