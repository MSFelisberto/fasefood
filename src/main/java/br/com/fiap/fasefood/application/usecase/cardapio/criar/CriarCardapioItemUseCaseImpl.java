package br.com.fiap.fasefood.application.usecase.cardapio.criar;

import br.com.fiap.fasefood.core.domain.entities.Cardapio;
import br.com.fiap.fasefood.core.domain.entities.CardapioItem;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioItemRepository;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioRepository;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CardapioItemResponseDTO;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CreateCardapioItemDTO;
import br.com.fiap.fasefood.infra.controller.mapper.cardapio.CardapioItemMapper;


public class CriarCardapioItemUseCaseImpl implements CriarCardapioItemUseCase {

    private final CardapioItemRepository cardapioItemRepository;
    private final CardapioRepository cardapioRepository;

    public CriarCardapioItemUseCaseImpl(CardapioItemRepository cardapioItemRepository, CardapioRepository cardapioRepository) {
        this.cardapioItemRepository = cardapioItemRepository;
        this.cardapioRepository = cardapioRepository;
    }

    @Override
    public CardapioItemResponseDTO criar(CreateCardapioItemDTO dto) {
        Cardapio cardapio = cardapioRepository.findById(dto.cardapioId())
                .orElseThrow(() -> new ResourceNotFoundException("Cardápio com ID: " + dto.cardapioId() + " não encontrado."));

        CardapioItem item = CardapioItemMapper.toDomain(dto, cardapio);
        CardapioItem novoItem = cardapioItemRepository.salvar(item);

        return CardapioItemMapper.toResponseDTO(novoItem);
    }
}