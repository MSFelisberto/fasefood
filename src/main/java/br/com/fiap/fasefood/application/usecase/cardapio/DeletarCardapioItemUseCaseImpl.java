package br.com.fiap.fasefood.application.usecase.cardapio;

import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioItemRepository;
import br.com.fiap.fasefood.core.usecase.interfaces.cardapio.DeletarCardapioItemUseCase;

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