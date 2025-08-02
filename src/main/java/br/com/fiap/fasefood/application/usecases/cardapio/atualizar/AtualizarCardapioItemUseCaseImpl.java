package br.com.fiap.fasefood.application.usecases.cardapio.atualizar;

import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioItemOutput;
import br.com.fiap.fasefood.application.usecases.cardapio.mappers.CardapioOutputMapper;
import br.com.fiap.fasefood.core.entities.CardapioItem;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.gateways.CardapioItemRepository;



public class AtualizarCardapioItemUseCaseImpl implements AtualizarCardapioItemUseCase {

    private final CardapioItemRepository cardapioItemRepository;

    public AtualizarCardapioItemUseCaseImpl(CardapioItemRepository cardapioItemRepository) {
        this.cardapioItemRepository = cardapioItemRepository;
    }

    @Override
    public CriarCardapioItemOutput atualizar(Long id, AtualizarCardapioItemInput input) {
        CardapioItem item = cardapioItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item de cardápio com ID: " + id + " não encontrado."));

        item.atualizar(input.nome(), input.descricao(), input.preco(), input.apenasNoLocal(), input.caminhoFoto());

        CardapioItem itemAtualizado = cardapioItemRepository.salvar(item);

        return CardapioOutputMapper.toCriarCardapioItemOutput(itemAtualizado);
    }
}