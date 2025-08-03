package br.com.fiap.fasefood.application.usecases.cardapio.criar;

import br.com.fiap.fasefood.core.entities.Cardapio;
import br.com.fiap.fasefood.core.entities.CardapioItem;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.gateways.CardapioItemRepository;
import br.com.fiap.fasefood.core.gateways.CardapioRepository;


public class CriarCardapioItemUseCaseImpl implements CriarCardapioItemUseCase {

    private final CardapioItemRepository cardapioItemRepository;
    private final CardapioRepository cardapioRepository;

    public CriarCardapioItemUseCaseImpl(CardapioItemRepository cardapioItemRepository, CardapioRepository cardapioRepository) {
        this.cardapioItemRepository = cardapioItemRepository;
        this.cardapioRepository = cardapioRepository;
    }

    @Override
    public CriarCardapioItemOutput criar(CriarCardapioItemInput input) {
        Cardapio cardapio = cardapioRepository.findById(input.cardapioId())
                .orElseThrow(() -> new ResourceNotFoundException("Cardápio com ID: " + input.cardapioId() + " não encontrado."));

        CardapioItem novoItem = cardapioItemRepository.salvar(
                CardapioItem.create(
                        null,
                        cardapio,
                        input.nome(),
                        input.descricao(),
                        input.preco(),
                        input.apenasNoLocal(),
                        input.caminhoFoto(),
                        true
                )
        );

        return new CriarCardapioItemOutput(
                novoItem.getId(),
                novoItem.getCardapio().getId(),
                novoItem.getNome(),
                novoItem.getDescricao(),
                novoItem.getPreco(),
                novoItem.isApenasNoLocal(),
                novoItem.getCaminhoFoto()
        );
    }
}