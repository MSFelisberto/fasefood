package br.com.fiap.fasefood.application.usecases.cardapio.criar;

import br.com.fiap.fasefood.application.usecases.cardapio.mappers.CardapioOutputMapper;
import br.com.fiap.fasefood.core.entities.Cardapio;
import br.com.fiap.fasefood.core.entities.CardapioItem;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.gateways.CardapioItemRepository;
import br.com.fiap.fasefood.core.gateways.CardapioRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CriarCardapioItemsBatchUseCaseImpl implements CriarCardapioItemsBatchUseCase {

    private final CardapioRepository cardapioRepository;
    private final CardapioItemRepository cardapioItemRepository;

    public CriarCardapioItemsBatchUseCaseImpl(CardapioRepository cardapioRepository, CardapioItemRepository cardapioItemRepository) {
        this.cardapioRepository = cardapioRepository;
        this.cardapioItemRepository = cardapioItemRepository;
    }

    @Override
    public List<CriarCardapioItemOutput> criarEmLote(CriarCardapioItemsBatchInput input) {
        Cardapio cardapio = cardapioRepository.findById(input.cardapioId())
                .orElseThrow(() -> new ResourceNotFoundException("Cardápio com ID: " + input.cardapioId() + " não encontrado."));

        List<CardapioItem> itensSalvos = new ArrayList<>();
        for (ItemCardapioInput itemInput : input.itens()) {
            CardapioItem item = CardapioItem.create(
                    null,
                    cardapio,
                    itemInput.nome(),
                    itemInput.descricao(),
                    itemInput.preco(),
                    itemInput.apenasNoLocal(),
                    itemInput.caminhoFoto(),
                    true);
            itensSalvos.add(cardapioItemRepository.salvar(item));
        }

        return itensSalvos.stream()
                .map(CardapioOutputMapper::toCriarCardapioItemOutput)
                .collect(Collectors.toList());
    }
}