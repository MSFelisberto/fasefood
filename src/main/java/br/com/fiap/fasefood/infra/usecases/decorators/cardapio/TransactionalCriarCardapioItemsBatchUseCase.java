package br.com.fiap.fasefood.infra.usecases.decorators.cardapio;

import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioItemOutput;
import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioItemsBatchInput;
import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioItemsBatchUseCase;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class TransactionalCriarCardapioItemsBatchUseCase implements CriarCardapioItemsBatchUseCase {

    private final CriarCardapioItemsBatchUseCase decorated;

    public TransactionalCriarCardapioItemsBatchUseCase(CriarCardapioItemsBatchUseCase decorated) {
        this.decorated = decorated;
    }

    @Override
    @Transactional
    public List<CriarCardapioItemOutput> criarEmLote(CriarCardapioItemsBatchInput dto) {
        return decorated.criarEmLote(dto);
    }
}
