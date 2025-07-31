package br.com.fiap.fasefood.application.usecases.cardapio.criar;

import br.com.fiap.fasefood.core.entities.Cardapio;
import br.com.fiap.fasefood.core.entities.CardapioItem;
import br.com.fiap.fasefood.core.entities.Restaurante;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.gateways.CardapioItemRepository;
import br.com.fiap.fasefood.core.gateways.CardapioRepository;
import br.com.fiap.fasefood.core.gateways.RestauranteRepository;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CardapioResponseDTO;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CreateCardapioDTO;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.ItensCreateCardapioItemDTO;
import br.com.fiap.fasefood.infra.controllers.mapper.cardapio.CardapioItemMapper;
import br.com.fiap.fasefood.infra.controllers.mapper.cardapio.CardapioMapper;
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