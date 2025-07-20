package br.com.fiap.fasefood.infra.controller;

import br.com.fiap.fasefood.core.domain.entities.Endereco;
import br.com.fiap.fasefood.core.domain.entities.restaurante.Restaurante;
import br.com.fiap.fasefood.core.usecase.restaurante.CriarRestauranteUseCase;
import br.com.fiap.fasefood.infra.controller.dto.restaurante.CriarRestauranteDTO;
import br.com.fiap.fasefood.infra.controller.dto.restaurante.RestauranteResponseDTO;
import br.com.fiap.fasefood.infra.persistence.RestauranteRespositoryImpl;
import br.com.fiap.fasefood.infra.persistence.UsuarioRepositoryImpl;
import br.com.fiap.fasefood.infra.persistence.jpa.RestauranteJpaRepository;
import br.com.fiap.fasefood.infra.persistence.jpa.UserJpaRepository;
import br.com.fiap.fasefood.infra.presenters.restaurante.RestaurantePresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/restaurantes")
public class RestauranteController {

    private final RestauranteJpaRepository restauranteJpaRepository;
    private final UserJpaRepository userJpaRepository;

    public RestauranteController(
            RestauranteJpaRepository restauranteJpaRepository,
            UserJpaRepository userJpaRepository
    ) {
        this.restauranteJpaRepository = restauranteJpaRepository;
        this.userJpaRepository = userJpaRepository;
    }

    @PostMapping
    public ResponseEntity<RestauranteResponseDTO> criarRestaurante(
            @RequestBody CriarRestauranteDTO data
    ) {
        CriarRestauranteUseCase usecase = new CriarRestauranteUseCase(
                new RestauranteRespositoryImpl(this.restauranteJpaRepository),
                new UsuarioRepositoryImpl(this.userJpaRepository)
        );

        Restaurante restaurante = usecase.execute(data);

        return ResponseEntity.ok(RestaurantePresenter.toDTO(restaurante));
    }
}
