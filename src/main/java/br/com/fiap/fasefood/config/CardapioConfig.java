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
    public CriarCardapioUseCase criarCardapioUseCase(CardapioRepository cardapioRepo, RestauranteRepository restauranteRepo) {
        return new CriarCardapioUseCaseImpl(cardapioRepo, restauranteRepo);
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
    public ListarCardapioItensUseCase listarCardapioItensUseCase(CardapioItemRepository itemRepo) {
        return new ListarCardapioItensUseCaseImpl(itemRepo);
    }

    @Bean
    public AtualizarCardapioItemUseCase atualizarCardapioItemUseCase(CardapioItemRepository itemRepo) {
        return new AtualizarCardapioItemUseCaseImpl(itemRepo);
    }

    @Bean
    public DeletarCardapioItemUseCase deletarCardapioItemUseCase(CardapioItemRepository itemRepo) {
        return new DeletarCardapioItemUseCaseImpl(itemRepo);
    }
}