package br.com.fiap.fasefood.application.usecases.cardapio.listar;

import br.com.fiap.fasefood.core.gateways.CardapioItemRepository;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CardapioItemResponseDTO;
import br.com.fiap.fasefood.infra.controllers.mapper.cardapio.CardapioItemMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ListarCardapioItensUseCaseImpl implements ListarCardapioItensUseCase {

    private final CardapioItemRepository cardapioItemRepository;

    public ListarCardapioItensUseCaseImpl(CardapioItemRepository cardapioItemRepository) {
        this.cardapioItemRepository = cardapioItemRepository;
    }

    @Override
    public Page<CardapioItemResponseDTO> listar(Long cardapioId, Pageable pageable) {
        return cardapioItemRepository.findByCardapioId(cardapioId, pageable)
                .map(CardapioItemMapper::toResponseDTO);
    }
}