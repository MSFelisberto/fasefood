package br.com.fiap.fasefood.application.usecase.cardapio;

import br.com.fiap.fasefood.core.domain.entities.Cardapio;
import br.com.fiap.fasefood.core.domain.entities.Restaurante;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioRepository;
import br.com.fiap.fasefood.core.usecase.gateways.RestauranteRepository;
import br.com.fiap.fasefood.core.usecase.interfaces.cardapio.CriarCardapioUseCase;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CardapioResponseDTO;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CreateCardapioDTO;
import br.com.fiap.fasefood.infra.controller.mapper.cardapio.CardapioMapper;

public class CriarCardapioUseCaseImpl implements CriarCardapioUseCase {

    private final CardapioRepository cardapioRepository;
    private final RestauranteRepository restauranteRepository;

    public CriarCardapioUseCaseImpl(CardapioRepository cardapioRepository, RestauranteRepository restauranteRepository) {
        this.cardapioRepository = cardapioRepository;
        this.restauranteRepository = restauranteRepository;
    }

    @Override
    public CardapioResponseDTO criar(CreateCardapioDTO dto) {
        Restaurante restaurante = restauranteRepository.findById(dto.restauranteId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante com ID: " + dto.restauranteId() + " não encontrado."));

        Cardapio cardapio = CardapioMapper.toDomain(dto, restaurante);
        Cardapio novoCardapio = cardapioRepository.salvar(cardapio);

        return CardapioMapper.toResponseDTO(novoCardapio);
    }
}