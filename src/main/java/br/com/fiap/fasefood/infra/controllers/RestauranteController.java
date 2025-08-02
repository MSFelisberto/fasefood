package br.com.fiap.fasefood.infra.controllers;

import br.com.fiap.fasefood.application.usecases.restaurante.RestauranteOutput;
import br.com.fiap.fasefood.application.usecases.restaurante.atualizar.AtualizarRestauranteUseCase;
import br.com.fiap.fasefood.application.usecases.restaurante.criar.CriarRestauranteUseCase;
import br.com.fiap.fasefood.application.usecases.restaurante.deletar.DeletarRestauranteUseCase;
import br.com.fiap.fasefood.application.usecases.restaurante.listar.BuscarRestaurantePorIdUseCase;
import br.com.fiap.fasefood.application.usecases.restaurante.listar.ListarRestaurantesUseCase;
import br.com.fiap.fasefood.infra.controllers.docs.RestauranteControllerDocs;
import br.com.fiap.fasefood.infra.controllers.dto.restaurante.CreateRestauranteDTO;
import br.com.fiap.fasefood.infra.controllers.dto.restaurante.RestauranteResponseDTO;
import br.com.fiap.fasefood.infra.controllers.dto.restaurante.UpdateRestauranteDTO;
import br.com.fiap.fasefood.infra.controllers.mapper.restaurante.RestauranteMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/restaurantes")
public class RestauranteController implements RestauranteControllerDocs {

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
            ListarRestaurantesUseCase listarRestaurantesUseCase
    ) {
        this.criarRestauranteUseCase = criarRestauranteUseCase;
        this.atualizarRestauranteUseCase = atualizarRestauranteUseCase;
        this.deletarRestauranteUseCase = deletarRestauranteUseCase;
        this.buscarRestaurantePorIdUseCase = buscarRestaurantePorIdUseCase;
        this.listarRestaurantesUseCase = listarRestaurantesUseCase;
    }

    @Override
    @PostMapping
    public ResponseEntity<RestauranteResponseDTO> criarRestaurante(
            @RequestBody @Valid CreateRestauranteDTO dto,
            UriComponentsBuilder uriBuilder
    ) {
        RestauranteOutput response = criarRestauranteUseCase.criar(
                RestauranteMapper.toCriarRestauranteInput(dto)
        );
        URI location = uriBuilder.path("/api/v1/restaurantes/{id}")
                .buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(RestauranteMapper.toResponseDTO(response));
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<RestauranteResponseDTO>> listarRestaurantes(
            @PageableDefault(size = 10, sort = {"nome"}) Pageable pageable
    ) {
        Page<RestauranteOutput> restaurantes = listarRestaurantesUseCase.listar(pageable);
        return ResponseEntity.ok(RestauranteMapper.toRestauranteDtoPaginacao(restaurantes));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> buscarRestaurantePorId(@PathVariable Long id) {
        RestauranteOutput restaurante = buscarRestaurantePorIdUseCase.buscarPorId(id);
        return ResponseEntity.ok(RestauranteMapper.toResponseDTO(restaurante));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> atualizarRestaurante(
            @PathVariable Long id,
            @RequestBody @Valid UpdateRestauranteDTO dto
    ) {
        RestauranteOutput restaurante = atualizarRestauranteUseCase.atualizar(
                id,
                RestauranteMapper.toUpdateRestauranteInput(dto)
        );
        return ResponseEntity.ok(RestauranteMapper.toResponseDTO(restaurante));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarRestaurante(@PathVariable Long id) {
        deletarRestauranteUseCase.deletar(id);
        return ResponseEntity.noContent().build();
    }
}