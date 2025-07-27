package br.com.fiap.fasefood.application.usecase.cardapio;

import br.com.fiap.fasefood.core.domain.entities.Cardapio;
import br.com.fiap.fasefood.core.domain.entities.CardapioItem;
import br.com.fiap.fasefood.core.domain.entities.Restaurante;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioItemRepository;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioRepository;
import br.com.fiap.fasefood.core.usecase.gateways.RestauranteRepository;
import br.com.fiap.fasefood.core.usecase.interfaces.cardapio.CriarCardapioUseCase;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CardapioResponseDTO;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CreateCardapioDTO;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.ItensCreateCardapioItemDTO;
import br.com.fiap.fasefood.infra.controller.mapper.cardapio.CardapioItemMapper;
import br.com.fiap.fasefood.infra.controller.mapper.cardapio.CardapioMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public class CriarCardapioUseCaseImpl implements CriarCardapioUseCase {

    private final CardapioRepository cardapioRepository;
    private final RestauranteRepository restauranteRepository;
    private final CardapioItemRepository cardapioItemRepository;

    public CriarCardapioUseCaseImpl(CardapioRepository cardapioRepository, RestauranteRepository restauranteRepository, CardapioItemRepository cardapioItemRepository) {
        this.cardapioRepository = cardapioRepository;
        this.restauranteRepository = restauranteRepository;
        this.cardapioItemRepository = cardapioItemRepository;
    }

    @Override
    @Transactional
    public CardapioResponseDTO criar(CreateCardapioDTO dto) {
        Restaurante restaurante = restauranteRepository.findById(dto.restauranteId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante com ID: " + dto.restauranteId() + " não encontrado."));

        Cardapio cardapio = CardapioMapper.toDomain(dto, restaurante);
        Cardapio novoCardapio = cardapioRepository.salvar(cardapio);

        List<CardapioItem> itensSalvos = new ArrayList<>();
        if (dto.itens() != null && !dto.itens().isEmpty()) {
            for (ItensCreateCardapioItemDTO itemDTO : dto.itens()) {
                CardapioItem item = CardapioItemMapper.toDomain(itemDTO, novoCardapio);
                itensSalvos.add(cardapioItemRepository.salvar(item));
            }
        }
        novoCardapio.setItens(itensSalvos);

        return CardapioMapper.toResponseDTO(novoCardapio);
    }
}