package br.com.fiap.fasefood.infra.usecases.decorators.cardapio;

import br.com.fiap.fasefood.application.usecases.cardapio.deletar.RemoverItensCardapioUseCase;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class TransactionalRemoverItensCardapioUseCase implements RemoverItensCardapioUseCase {

    private final RemoverItensCardapioUseCase decorated;

    public TransactionalRemoverItensCardapioUseCase(RemoverItensCardapioUseCase decorated) {
        this.decorated = decorated;
    }

    @Override
    @Transactional
    public void removerEmLote(List<Long> ids) {
        decorated.removerEmLote(ids);
    }
}
