package br.com.fiap.fasefood.application.usecases.cardapio.listar;

import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioItemOutput;
import br.com.fiap.fasefood.application.usecases.cardapio.mappers.CardapioOutputMapper;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PageOutput;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PaginationInput;
import br.com.fiap.fasefood.core.gateways.CardapioItemRepository;

public class ListarCardapioItensUseCaseImpl implements ListarCardapioItensUseCase {

    private final CardapioItemRepository cardapioItemRepository;

    public ListarCardapioItensUseCaseImpl(CardapioItemRepository cardapioItemRepository) {
        this.cardapioItemRepository = cardapioItemRepository;
    }

    @Override
    public PageOutput<CriarCardapioItemOutput> listar(Long cardapioId, PaginationInput pageable) {
        var paginaDeItensDeDominio = cardapioItemRepository.findByCardapioId(cardapioId, pageable);

        var conteudoDeOutput = paginaDeItensDeDominio.content().stream()
                .map(CardapioOutputMapper::toCriarCardapioItemOutput)
                .toList();

        return new PageOutput<>(
                conteudoDeOutput,
                paginaDeItensDeDominio.currentPage(),
                paginaDeItensDeDominio.size(),
                paginaDeItensDeDominio.totalPages(),
                paginaDeItensDeDominio.totalElements()
        );
    }
}