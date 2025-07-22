package br.com.fiap.fasefood.infra.controller;

import br.com.fiap.fasefood.core.domain.entities.restaurante.Restaurante;
import br.com.fiap.fasefood.core.usecase.interfaces.restaurante.BuscarRestaurantePorIdUseCase;
import br.com.fiap.fasefood.core.usecase.interfaces.restaurante.CriarRestauranteUsecase;
import br.com.fiap.fasefood.core.usecase.interfaces.restaurante.DeletarRestaurantePorIdUseCase;
import br.com.fiap.fasefood.core.usecase.interfaces.restaurante.ListarTodosRestaurantesUseCase;
import br.com.fiap.fasefood.infra.controller.dto.restaurante.CriarRestauranteDTO;
import br.com.fiap.fasefood.infra.controller.dto.restaurante.RestauranteResponseDTO;
import br.com.fiap.fasefood.infra.presenters.restaurante.RestaurantePresenter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/restaurantes")
public class RestauranteController {

    private final CriarRestauranteUsecase criarRestauranteUsecase;
    private final ListarTodosRestaurantesUseCase listarTodosRestaurantesUseCase;
    private final BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase;
    private final DeletarRestaurantePorIdUseCase deletarRestaurantePorIdUseCase;

    public RestauranteController(
            CriarRestauranteUsecase criarRestauranteUsecase,
            ListarTodosRestaurantesUseCase listarTodosRestaurantesUseCase,
            BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase,
            DeletarRestaurantePorIdUseCase deletarRestaurantePorIdUseCase
    ) {
        this.criarRestauranteUsecase = criarRestauranteUsecase;
        this.listarTodosRestaurantesUseCase = listarTodosRestaurantesUseCase;
        this.buscarRestaurantePorIdUseCase = buscarRestaurantePorIdUseCase;
        this.deletarRestaurantePorIdUseCase = deletarRestaurantePorIdUseCase;
    }

    @PostMapping
    public ResponseEntity<RestauranteResponseDTO> criarRestaurante(
            @RequestBody CriarRestauranteDTO data
    ) {
        Restaurante restaurante = this.criarRestauranteUsecase.criarRestaurante(data);
        return ResponseEntity.ok(RestaurantePresenter.toDTO(restaurante));
    }

    @GetMapping
    public ResponseEntity<Page<RestauranteResponseDTO>> listarTodosRestaurantes(
            Pageable pageable
    ) {
        Page<Restaurante> restaurantes = this.listarTodosRestaurantesUseCase.buscaTodosRestaurantes(pageable);
        return ResponseEntity.ok(restaurantes.map(RestaurantePresenter::toDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> buscarRestaurantePorId(
            @PathVariable Long id
    ) {
        Restaurante restaurante = this.buscarRestaurantePorIdUseCase.buscaRestaurantePorId(id);
        return ResponseEntity.ok(RestaurantePresenter.toDTO(restaurante));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarRestaurantePorId(
            @PathVariable Long id
    ) {
        this.deletarRestaurantePorIdUseCase.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
