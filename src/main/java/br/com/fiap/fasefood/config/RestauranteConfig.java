package br.com.fiap.fasefood.config;

import br.com.fiap.fasefood.application.usecase.restaurante.*;
import br.com.fiap.fasefood.core.usecase.gateways.RestauranteRepository;
import br.com.fiap.fasefood.core.usecase.gateways.UsuarioRepository;
import br.com.fiap.fasefood.core.usecase.interfaces.restaurante.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestauranteConfig {

    @Bean
    public CriarRestauranteUseCase criarRestauranteUseCase(RestauranteRepository restauranteRepository, UsuarioRepository usuarioRepository) {
        return new CriarRestauranteUseCaseImpl(restauranteRepository, usuarioRepository);
    }

    @Bean
    public AtualizarRestauranteUseCase atualizarRestauranteUseCase(RestauranteRepository restauranteRepository) {
        return new AtualizarRestauranteUseCaseImpl(restauranteRepository);
    }

    @Bean
    public DeletarRestauranteUseCase deletarRestauranteUseCase(RestauranteRepository restauranteRepository) {
        return new DeletarRestauranteUseCaseImpl(restauranteRepository);
    }

    @Bean
    public BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase(RestauranteRepository restauranteRepository) {
        return new BuscarRestaurantePorIdUseCaseImpl(restauranteRepository);
    }

    @Bean
    public ListarRestaurantesUseCase listarRestaurantesUseCase(RestauranteRepository restauranteRepository) {
        return new ListarRestaurantesUseCaseImpl(restauranteRepository);
    }
}