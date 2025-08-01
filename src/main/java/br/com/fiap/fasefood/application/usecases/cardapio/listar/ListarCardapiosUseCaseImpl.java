package br.com.fiap.fasefood.application.usecases.cardapio.listar;

import br.com.fiap.fasefood.application.usecases.cardapio.mappers.CardapioOutputMapper;
import br.com.fiap.fasefood.core.gateways.CardapioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ListarCardapiosUseCaseImpl implements ListarCardapiosUseCase {

    private final CardapioRepository cardapioRepository;

    public ListarCardapiosUseCaseImpl(CardapioRepository cardapioRepository) {
        this.cardapioRepository = cardapioRepository;
    }

    @Override
    public Page<CardapioResponseOutput> listar(Long restauranteId, Pageable pageable) {
        return cardapioRepository.findByRestauranteId(restauranteId, pageable)
                .map(CardapioOutputMapper::toCardapioResponseOutput);
    }
}