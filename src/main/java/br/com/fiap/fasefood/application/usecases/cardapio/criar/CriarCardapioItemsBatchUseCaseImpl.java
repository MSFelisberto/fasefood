package br.com.fiap.fasefood.application.usecases.cardapio.criar;

import br.com.fiap.fasefood.core.entities.Cardapio;
import br.com.fiap.fasefood.core.entities.CardapioItem;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.gateways.CardapioItemRepository;
import br.com.fiap.fasefood.core.gateways.CardapioRepository;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CardapioItemResponseDTO;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CreateCardapioItemsBatchDTO;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.ItensCreateCardapioItemDTO;
import br.com.fiap.fasefood.infra.controllers.mapper.cardapio.CardapioItemMapper;
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