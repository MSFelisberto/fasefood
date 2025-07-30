package br.com.fiap.fasefood.application.usecase.cardapio.atualizar;

import br.com.fiap.fasefood.core.domain.entities.CardapioItem;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioItemRepository;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CardapioItemResponseDTO;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.UpdateCardapioItemRequestDTO;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.UpdateCardapioItemsBatchDTO;
import br.com.fiap.fasefood.infra.controller.mapper.cardapio.CardapioItemMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AtualizarCardapioItensBatchUseCaseImpl implements AtualizarCardapioItensBatchUseCase {

    private final CardapioItemRepository cardapioItemRepository;

    public AtualizarCardapioItensBatchUseCaseImpl(CardapioItemRepository cardapioItemRepository) {
        this.cardapioItemRepository = cardapioItemRepository;
    }

    @Override
    @Transactional
    public List<CardapioItemResponseDTO> atualizarEmLote(UpdateCardapioItemsBatchDTO dto) {
        List<CardapioItem> itensAtualizados = new ArrayList<>();

        for (UpdateCardapioItemRequestDTO itemDTO : dto.itens()) {
            CardapioItem item = cardapioItemRepository.findById(itemDTO.id())
                    .orElseThrow(() -> new ResourceNotFoundException("Item de cardápio com ID: " + itemDTO.id() + " não encontrado."));

            item.atualizar(
                    itemDTO.nome(),
                    itemDTO.descricao(),
                    itemDTO.preco(),
                    itemDTO.apenasNoLocal(),
                    itemDTO.caminhoFoto()
            );

            itensAtualizados.add(cardapioItemRepository.salvar(item));
        }

        return itensAtualizados.stream()
                .map(CardapioItemMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}