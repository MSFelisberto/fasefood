package br.com.fiap.fasefood.infra.usecases.decorators.restaurante;

import br.com.fiap.fasefood.application.usecases.restaurante.RestauranteOutput;
import br.com.fiap.fasefood.application.usecases.restaurante.atualizar.AtualizarRestauranteUseCase;
import br.com.fiap.fasefood.application.usecases.restaurante.atualizar.UpdateRestauranteInput;
import org.springframework.transaction.annotation.Transactional;

public class TransactionalAtualizarRestauranteUseCase implements AtualizarRestauranteUseCase {

    private final AtualizarRestauranteUseCase decorator;

    public TransactionalAtualizarRestauranteUseCase(AtualizarRestauranteUseCase decorator) {
        this.decorator = decorator;
    }

    @Override
    @Transactional
    public RestauranteOutput atualizar(Long id, UpdateRestauranteInput input) {
        return decorator.atualizar(id, input);
    }
}
