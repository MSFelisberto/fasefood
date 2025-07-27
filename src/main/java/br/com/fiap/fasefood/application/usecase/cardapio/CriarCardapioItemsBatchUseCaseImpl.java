package br.com.fiap.fasefood.application.usecase.cardapio;

import br.com.fiap.fasefood.core.domain.entities.Cardapio;
import br.com.fiap.fasefood.core.domain.entities.CardapioItem;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioItemRepository;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioRepository;
import br.com.fiap.fasefood.core.usecase.interfaces.cardapio.CriarCardapioItemsBatchUseCase;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CardapioItemResponseDTO;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CreateCardapioItemsBatchDTO;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.ItensCreateCardapioItemDTO;
import br.com.fiap.fasefood.infra.controller.mapper.cardapio.CardapioItemMapper;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public List<CardapioItemResponseDTO> criarEmLote(CreateCardapioItemsBatchDTO dto) {
        Cardapio cardapio = cardapioRepository.findById(dto.cardapioId())
                .orElseThrow(() -> new ResourceNotFoundException("Cardápio com ID: " + dto.cardapioId() + " não encontrado."));

        List<CardapioItem> itensSalvos = new ArrayList<>();
        for (ItensCreateCardapioItemDTO itemDTO : dto.itens()) {
            CardapioItem item = CardapioItemMapper.toDomain(itemDTO, cardapio);
            itensSalvos.add(cardapioItemRepository.salvar(item));
        }

        return itensSalvos.stream()
                .map(CardapioItemMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}