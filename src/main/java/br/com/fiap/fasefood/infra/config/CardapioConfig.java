package br.com.fiap.fasefood.infra.config;

import br.com.fiap.fasefood.application.usecases.cardapio.atualizar.AtualizarCardapioItemUseCase;
import br.com.fiap.fasefood.application.usecases.cardapio.atualizar.AtualizarCardapioItemUseCaseImpl;
import br.com.fiap.fasefood.application.usecases.cardapio.atualizar.AtualizarCardapioItensBatchUseCase;
import br.com.fiap.fasefood.application.usecases.cardapio.atualizar.AtualizarCardapioItensBatchUseCaseImpl;
import br.com.fiap.fasefood.application.usecases.cardapio.criar.*;
import br.com.fiap.fasefood.application.usecases.cardapio.deletar.RemoverCardapioItemUseCase;
import br.com.fiap.fasefood.application.usecases.cardapio.deletar.RemoverCardapioItemUseCaseImpl;
import br.com.fiap.fasefood.application.usecases.cardapio.deletar.RemoverItensCardapioUseCase;
import br.com.fiap.fasefood.application.usecases.cardapio.deletar.RemoverItensCardapioUseCaseImpl;
import br.com.fiap.fasefood.application.usecases.cardapio.listar.ListarCardapioItensUseCase;
import br.com.fiap.fasefood.application.usecases.cardapio.listar.ListarCardapioItensUseCaseImpl;
import br.com.fiap.fasefood.application.usecases.cardapio.listar.ListarCardapiosUseCase;
import br.com.fiap.fasefood.application.usecases.cardapio.listar.ListarCardapiosUseCaseImpl;
import br.com.fiap.fasefood.core.gateways.CardapioItemRepository;
import br.com.fiap.fasefood.core.gateways.CardapioRepository;
import br.com.fiap.fasefood.core.gateways.RestauranteRepository;
import br.com.fiap.fasefood.infra.usecases.decorators.cardapio.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CardapioConfig {

    @Bean
    public CriarCardapioUseCase criarCardapioUseCase(CardapioRepository cardapioRepo, RestauranteRepository restauranteRepo, CardapioItemRepository itemRepo) {
        var impl = new CriarCardapioUseCaseImpl(cardapioRepo, restauranteRepo, itemRepo);
        return new TransactionalCriarCardapioUseCase(impl);
    }

    @Bean
    public ListarCardapiosUseCase listarCardapiosUseCase(CardapioRepository cardapioRepo) {
        return new ListarCardapiosUseCaseImpl(cardapioRepo);
    }

    @Bean
    public CriarCardapioItemUseCase criarCardapioItemUseCase(CardapioItemRepository itemRepo, CardapioRepository cardapioRepo) {
        return new CriarCardapioItemUseCaseImpl(itemRepo, cardapioRepo);
    }

    @Bean
    public CriarCardapioItemsBatchUseCase criarCardapioItemsBatchUseCase(CardapioRepository cardapioRepo, CardapioItemRepository itemRepo) {
        var impl = new CriarCardapioItemsBatchUseCaseImpl(cardapioRepo, itemRepo);
        return new TransactionalCriarCardapioItemsBatchUseCase(impl);
    }

    @Bean
    public ListarCardapioItensUseCase listarCardapioItensUseCase(CardapioItemRepository itemRepo) {
        return new ListarCardapioItensUseCaseImpl(itemRepo);
    }

    @Bean
    public AtualizarCardapioItemUseCase atualizarCardapioItemUseCase(CardapioItemRepository itemRepo) {
        var impl = new AtualizarCardapioItemUseCaseImpl(itemRepo);
        return new TransactionalAtualizarCardapioItemUseCase(impl);
    }

    @Bean
    public AtualizarCardapioItensBatchUseCase atualizarCardapioItensBatchUseCase(CardapioItemRepository itemRepo) {
        var impl = new AtualizarCardapioItensBatchUseCaseImpl(itemRepo);
        return new TransactionalAtualizarCardapioItensBatchUseCase(impl);
    }

    @Bean
    public RemoverCardapioItemUseCase removerCardapioItemUseCase(CardapioItemRepository itemRepo) {
        return new RemoverCardapioItemUseCaseImpl(itemRepo);
    }

    @Bean
    public RemoverItensCardapioUseCase removerItensCardapioUseCase(CardapioItemRepository itemRepo) {
        var impl = new RemoverItensCardapioUseCaseImpl(itemRepo);
        return new TransactionalRemoverItensCardapioUseCase(impl);
    }
}