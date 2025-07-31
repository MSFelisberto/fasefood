package br.com.fiap.fasefood.config;

import br.com.fiap.fasefood.application.usecase.cardapio.*;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioItemRepository;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioRepository;
import br.com.fiap.fasefood.core.usecase.gateways.RestauranteRepository;
import br.com.fiap.fasefood.core.usecase.interfaces.cardapio.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CardapioConfig {

    @Bean
    public CriarCardapioUseCase criarCardapioUseCase(CardapioRepository cardapioRepo, RestauranteRepository restauranteRepo, CardapioItemRepository itemRepo) {
        return new CriarCardapioUseCaseImpl(cardapioRepo, restauranteRepo, itemRepo);
    }

    @Bean
    public ListarCardapiosUseCase listarCardapiosUseCase(CardapioRepository cardapioRepo) {
        return new ListarCardapiosUseCaseImpl(cardapioRepo);
    }

    @Bean
    public CriarCardapioItemUseCase criarCardapioItemUseCase(CardapioItemRepository itemRepo, CardapioRepository cardapioRepo) {
        return new CriarCardapioItemUseCaseImpl(cardapioRepo, itemRepo);
    }

    @Bean
    public CriarCardapioItemsBatchUseCase criarCardapioItemsBatchUseCase(CardapioRepository cardapioRepo, CardapioItemRepository itemRepo) {
        return new CriarCardapioItemsBatchUseCaseImpl(cardapioRepo, itemRepo);
    }

    @Bean
    public ListarCardapioItensUseCase listarCardapioItensUseCase(CardapioItemRepository itemRepo) {
        return new ListarCardapioItensUseCaseImpl(itemRepo);
    }

    @Bean
    public AtualizarCardapioItemUseCase atualizarCardapioItemUseCase(CardapioItemRepository itemRepo) {
        return new AtualizarCardapioItemUseCaseImpl(itemRepo);
    }

    @Bean
    public AtualizarCardapioItensBatchUseCase atualizarCardapioItensBatchUseCase(CardapioItemRepository itemRepo) {
        return new AtualizarCardapioItensBatchUseCaseImpl(itemRepo);
    }

    @Bean
    public DeletarCardapioItemUseCase deletarCardapioItemUseCase(CardapioItemRepository itemRepo) {
        return new DeletarCardapioItemUseCaseImpl(itemRepo);
    }
}