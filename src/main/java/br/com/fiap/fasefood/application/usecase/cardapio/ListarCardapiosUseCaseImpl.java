package br.com.fiap.fasefood.application.usecase.cardapio;

import br.com.fiap.fasefood.core.usecase.gateways.CardapioRepository;
import br.com.fiap.fasefood.core.usecase.interfaces.cardapio.ListarCardapiosUseCase;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CardapioResponseDTO;
import br.com.fiap.fasefood.infra.controller.mapper.cardapio.CardapioMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ListarCardapiosUseCaseImpl implements ListarCardapiosUseCase {

    private final CardapioRepository cardapioRepository;

    public ListarCardapiosUseCaseImpl(CardapioRepository cardapioRepository) {
        this.cardapioRepository = cardapioRepository;
    }

    @Override
    public Page<CardapioResponseDTO> listar(Long restauranteId, Pageable pageable) {
        return cardapioRepository.findByRestauranteId(restauranteId, pageable)
                .map(CardapioMapper::toResponseDTO);
    }
}