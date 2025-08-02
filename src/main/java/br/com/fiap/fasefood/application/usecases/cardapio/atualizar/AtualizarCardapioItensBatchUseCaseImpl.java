package br.com.fiap.fasefood.application.usecases.cardapio.atualizar;

import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioItemOutput;
import br.com.fiap.fasefood.application.usecases.cardapio.mappers.CardapioOutputMapper;
import br.com.fiap.fasefood.core.entities.CardapioItem;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.gateways.CardapioItemRepository;



import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AtualizarCardapioItensBatchUseCaseImpl implements AtualizarCardapioItensBatchUseCase {

    private final CardapioItemRepository cardapioItemRepository;

    public AtualizarCardapioItensBatchUseCaseImpl(CardapioItemRepository cardapioItemRepository) {
        this.cardapioItemRepository = cardapioItemRepository;
    }

    @Override
    public List<CriarCardapioItemOutput> atualizarEmLote(List<AtualizarCardapioItemBatchInput> inputs) {
        List<CardapioItem> itensAtualizados = new ArrayList<>();

        for (AtualizarCardapioItemBatchInput itemInput : inputs) {
            CardapioItem item = cardapioItemRepository.findById(itemInput.id())
                    .orElseThrow(() -> new ResourceNotFoundException("Item de cardápio com ID: " + itemInput.id() + " não encontrado."));

            item.atualizar(
                    itemInput.nome(),
                    itemInput.descricao(),
                    itemInput.preco(),
                    itemInput.apenasNoLocal(),
                    itemInput.caminhoFoto()
            );

            itensAtualizados.add(cardapioItemRepository.salvar(item));
        }

        return itensAtualizados.stream()
                .map(CardapioOutputMapper::toCriarCardapioItemOutput)
                .collect(Collectors.toList());
    }
}