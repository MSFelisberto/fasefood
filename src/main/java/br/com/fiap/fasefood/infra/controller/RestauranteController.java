package br.com.fiap.fasefood.infra.controller;

import br.com.fiap.fasefood.core.usecase.interfaces.restaurante.*;
import br.com.fiap.fasefood.infra.controller.dto.restaurante.CreateRestauranteDTO;
import br.com.fiap.fasefood.infra.controller.dto.restaurante.RestauranteResponseDTO;
import br.com.fiap.fasefood.infra.controller.dto.restaurante.UpdateRestauranteDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Tag(name = "Restaurantes", description = "Endpoints para gerenciamento de restaurantes")
@RestController
@RequestMapping("/api/v1/restaurantes")
public class RestauranteController {

    private final CriarRestauranteUseCase criarRestauranteUseCase;
    private final AtualizarRestauranteUseCase atualizarRestauranteUseCase;
    private final DeletarRestauranteUseCase deletarRestauranteUseCase;
    private final BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase;
    private final ListarRestaurantesUseCase listarRestaurantesUseCase;

    public RestauranteController(
            CriarRestauranteUseCase criarRestauranteUseCase,
            AtualizarRestauranteUseCase atualizarRestauranteUseCase,
            DeletarRestauranteUseCase deletarRestauranteUseCase,
            BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase,
            ListarRestaurantesUseCase listarRestaurantesUseCase) {
        this.criarRestauranteUseCase = criarRestauranteUseCase;
        this.atualizarRestauranteUseCase = atualizarRestauranteUseCase;
        this.deletarRestauranteUseCase = deletarRestauranteUseCase;
        this.buscarRestaurantePorIdUseCase = buscarRestaurantePorIdUseCase;
        this.listarRestaurantesUseCase = listarRestaurantesUseCase;
    }

    @PostMapping
    public ResponseEntity<RestauranteResponseDTO> criarRestaurante(
            @RequestBody @Valid CreateRestauranteDTO dto,
            UriComponentsBuilder uriBuilder
    ) {
        RestauranteResponseDTO response = criarRestauranteUseCase.criar(dto);
        URI location = uriBuilder.path("/api/v1/restaurantes/{id}")
                .buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<RestauranteResponseDTO>> listarRestaurantes(
            @PageableDefault(size = 10, sort = {"nome"}) Pageable pageable
    ) {
        Page<RestauranteResponseDTO> restaurantes = listarRestaurantesUseCase.listar(pageable);
        return ResponseEntity.ok(restaurantes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> buscarRestaurantePorId(@PathVariable Long id) {
        RestauranteResponseDTO restaurante = buscarRestaurantePorIdUseCase.buscarPorId(id);
        return ResponseEntity.ok(restaurante);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> atualizarRestaurante(
            @PathVariable Long id,
            @RequestBody @Valid UpdateRestauranteDTO dto
    ) {
        RestauranteResponseDTO restaurante = atualizarRestauranteUseCase.atualizar(id, dto);
        return ResponseEntity.ok(restaurante);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarRestaurante(@PathVariable Long id) {
        deletarRestauranteUseCase.deletar(id);
        return ResponseEntity.noContent().build();
    }
}