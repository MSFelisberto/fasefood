package br.com.fiap.fasefood.application.usecase.cardapio;

import br.com.fiap.fasefood.core.domain.entities.CardapioItem;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioItemRepository;
import br.com.fiap.fasefood.core.usecase.interfaces.cardapio.AtualizarCardapioItemUseCase;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CardapioItemResponseDTO;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.UpdateCardapioItemDTO;
import br.com.fiap.fasefood.infra.controller.mapper.cardapio.CardapioItemMapper;
import org.springframework.transaction.annotation.Transactional;

public class AtualizarCardapioItemUseCaseImpl implements AtualizarCardapioItemUseCase {

    private final CardapioItemRepository cardapioItemRepository;

    public AtualizarCardapioItemUseCaseImpl(CardapioItemRepository cardapioItemRepository) {
        this.cardapioItemRepository = cardapioItemRepository;
    }

    @Override
    @Transactional
    public CardapioItemResponseDTO atualizar(Long id, UpdateCardapioItemDTO dto) {
        CardapioItem item = cardapioItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item de cardápio com ID: " + id + " não encontrado."));

        item.atualizar(dto.nome(), dto.descricao(), dto.preco(), dto.apenasNoLocal(), dto.caminhoFoto());

        CardapioItem itemAtualizado = cardapioItemRepository.salvar(item);
        return CardapioItemMapper.toResponseDTO(itemAtualizado);
    }
}