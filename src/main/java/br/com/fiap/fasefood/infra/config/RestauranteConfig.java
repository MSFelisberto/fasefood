package br.com.fiap.fasefood.infra.config;

import br.com.fiap.fasefood.application.usecases.restaurante.atualizar.AtualizarRestauranteUseCase;
import br.com.fiap.fasefood.application.usecases.restaurante.atualizar.AtualizarRestauranteUseCaseImpl;
import br.com.fiap.fasefood.application.usecases.restaurante.criar.CriarRestauranteUseCase;
import br.com.fiap.fasefood.application.usecases.restaurante.criar.CriarRestauranteUseCaseImpl;
import br.com.fiap.fasefood.application.usecases.restaurante.deletar.DeletarRestauranteUseCase;
import br.com.fiap.fasefood.application.usecases.restaurante.deletar.DeletarRestauranteUseCaseImpl;
import br.com.fiap.fasefood.application.usecases.restaurante.listar.BuscarRestaurantePorIdUseCase;
import br.com.fiap.fasefood.application.usecases.restaurante.listar.BuscarRestaurantePorIdUseCaseImpl;
import br.com.fiap.fasefood.application.usecases.restaurante.listar.ListarRestaurantesUseCase;
import br.com.fiap.fasefood.application.usecases.restaurante.listar.ListarRestaurantesUseCaseImpl;
import br.com.fiap.fasefood.core.gateways.RestauranteRepository;
import br.com.fiap.fasefood.core.gateways.UsuarioRepository;
import br.com.fiap.fasefood.infra.usecases.decorators.restaurante.TransactionalAtualizarRestauranteUseCase;
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
        var impl = new AtualizarRestauranteUseCaseImpl(restauranteRepository);
        return new TransactionalAtualizarRestauranteUseCase(impl);
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