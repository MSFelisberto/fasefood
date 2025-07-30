package br.com.fiap.fasefood.config;

import br.com.fiap.fasefood.application.usecase.cardapio.atualizar.AtualizarCardapioItemUseCase;
import br.com.fiap.fasefood.application.usecase.cardapio.atualizar.AtualizarCardapioItemUseCaseImpl;
import br.com.fiap.fasefood.application.usecase.cardapio.atualizar.AtualizarCardapioItensBatchUseCase;
import br.com.fiap.fasefood.application.usecase.cardapio.atualizar.AtualizarCardapioItensBatchUseCaseImpl;
import br.com.fiap.fasefood.application.usecase.cardapio.criar.*;
import br.com.fiap.fasefood.application.usecase.cardapio.deletar.DeletarCardapioItemUseCase;
import br.com.fiap.fasefood.application.usecase.cardapio.deletar.DeletarCardapioItemUseCaseImpl;
import br.com.fiap.fasefood.application.usecase.cardapio.listar.ListarCardapioItensUseCase;
import br.com.fiap.fasefood.application.usecase.cardapio.listar.ListarCardapioItensUseCaseImpl;
import br.com.fiap.fasefood.application.usecase.cardapio.listar.ListarCardapiosUseCase;
import br.com.fiap.fasefood.application.usecase.cardapio.listar.ListarCardapiosUseCaseImpl;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioItemRepository;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioRepository;
import br.com.fiap.fasefood.core.usecase.gateways.RestauranteRepository;
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
        return new CriarCardapioItemUseCaseImpl(itemRepo, cardapioRepo);
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