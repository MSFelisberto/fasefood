package br.com.fiap.fasefood.infra.usecases.decorators.cardapio;

import br.com.fiap.fasefood.application.usecases.cardapio.atualizar.AtualizarCardapioItemBatchInput;
import br.com.fiap.fasefood.application.usecases.cardapio.atualizar.AtualizarCardapioItensBatchUseCase;
import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioItemOutput;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class TransactionalAtualizarCardapioItensBatchUseCase implements AtualizarCardapioItensBatchUseCase {

    private final AtualizarCardapioItensBatchUseCase decorated;

    public TransactionalAtualizarCardapioItensBatchUseCase(AtualizarCardapioItensBatchUseCase decorated) {
        this.decorated = decorated;
    }

    @Override
    @Transactional
    public List<CriarCardapioItemOutput> atualizarEmLote(List<AtualizarCardapioItemBatchInput> inputs) {
        return decorated.atualizarEmLote(inputs);
    }
}
