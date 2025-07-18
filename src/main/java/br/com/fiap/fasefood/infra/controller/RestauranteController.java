package br.com.fiap.fasefood.infra.controller;

import br.com.fiap.fasefood.application.usecase.restaurante.CriarRestauranteUseCaseImpl;
import br.com.fiap.fasefood.infra.persistence.jpa.RestauranteJpaRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/restaurantes")
public class RestauranteController {

    private final RestauranteJpaRepository restauranteJpaRepository;

    public RestauranteController(RestauranteJpaRepository restauranteJpaRepository) {
        this.restauranteJpaRepository = restauranteJpaRepository;
    }

    @PostMapping
    public void criarRestaurante() {
//        new CriarRestauranteUseCaseImpl(restauranteJpaRepository);
    }
}
