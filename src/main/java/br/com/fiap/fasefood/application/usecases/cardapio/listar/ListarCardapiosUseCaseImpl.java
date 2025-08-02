package br.com.fiap.fasefood.application.usecases.cardapio.listar;

import br.com.fiap.fasefood.application.usecases.cardapio.mappers.CardapioOutputMapper;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PageOutput;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PaginationInput;
import br.com.fiap.fasefood.core.gateways.CardapioRepository;

public class ListarCardapiosUseCaseImpl implements ListarCardapiosUseCase {

    private final CardapioRepository cardapioRepository;

    public ListarCardapiosUseCaseImpl(CardapioRepository cardapioRepository) {
        this.cardapioRepository = cardapioRepository;
    }

    @Override
    public PageOutput<CardapioResponseOutput> listar(Long restauranteId, PaginationInput pageable) {
        var cardapioPage = cardapioRepository.findByRestauranteId(restauranteId, pageable);

        var content = cardapioPage.content().stream()
                .map(CardapioOutputMapper::toCardapioResponseOutput)
                .toList();

        return new PageOutput<>(
                content,
                cardapioPage.currentPage(),
                cardapioPage.size(),
                cardapioPage.totalPages(),
                cardapioPage.totalElements()
        );
    }
}