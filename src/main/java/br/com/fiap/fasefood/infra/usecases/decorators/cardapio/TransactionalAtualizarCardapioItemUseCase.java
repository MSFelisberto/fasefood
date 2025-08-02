package br.com.fiap.fasefood.infra.usecases.decorators.cardapio;

import br.com.fiap.fasefood.application.usecases.cardapio.atualizar.AtualizarCardapioItemInput;
import br.com.fiap.fasefood.application.usecases.cardapio.atualizar.AtualizarCardapioItemUseCase;
import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioItemOutput;
import org.springframework.transaction.annotation.Transactional;

public class TransactionalAtualizarCardapioItemUseCase implements AtualizarCardapioItemUseCase {

    private final AtualizarCardapioItemUseCase decorated;

    public TransactionalAtualizarCardapioItemUseCase(AtualizarCardapioItemUseCase decorated) {
        this.decorated = decorated;
    }

    @Override
    @Transactional
    public CriarCardapioItemOutput atualizar(Long id, AtualizarCardapioItemInput input) {
        return decorated.atualizar(id, input);
    }
}
