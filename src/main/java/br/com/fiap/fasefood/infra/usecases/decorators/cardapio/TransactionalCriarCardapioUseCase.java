package br.com.fiap.fasefood.infra.usecases.decorators.cardapio;

import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioInput;
import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioOutput;
import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioUseCase;
import org.springframework.transaction.annotation.Transactional;

public class TransactionalCriarCardapioUseCase implements CriarCardapioUseCase {

    private final CriarCardapioUseCase decorated;

    public TransactionalCriarCardapioUseCase(CriarCardapioUseCase decorated) {
        this.decorated = decorated;
    }

    @Override
    @Transactional
    public CriarCardapioOutput criar(CriarCardapioInput criarCardapioInput) {
        return decorated.criar(criarCardapioInput);
    }
}
