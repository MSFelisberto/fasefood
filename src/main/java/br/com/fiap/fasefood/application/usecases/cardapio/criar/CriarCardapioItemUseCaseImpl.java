package br.com.fiap.fasefood.application.usecases.cardapio.criar;

import br.com.fiap.fasefood.core.entities.Cardapio;
import br.com.fiap.fasefood.core.entities.CardapioItem;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.gateways.CardapioItemRepository;
import br.com.fiap.fasefood.core.gateways.CardapioRepository;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CardapioItemResponseDTO;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CreateCardapioItemDTO;
import br.com.fiap.fasefood.infra.controllers.mapper.cardapio.CardapioItemMapper;


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