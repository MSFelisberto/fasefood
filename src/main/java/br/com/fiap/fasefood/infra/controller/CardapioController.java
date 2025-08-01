package br.com.fiap.fasefood.infra.controller;

import br.com.fiap.fasefood.core.usecase.interfaces.cardapio.CriarCardapioUseCase;
import br.com.fiap.fasefood.core.usecase.interfaces.cardapio.ListarCardapiosUseCase;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CardapioResponseDTO;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CreateCardapioDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/cardapios")
public class CardapioController {

    private final CriarCardapioUseCase criarUseCase;
    private final ListarCardapiosUseCase listarUseCase;

    public CardapioController(CriarCardapioUseCase criarUseCase, ListarCardapiosUseCase listarUseCase) {
        this.criarUseCase = criarUseCase;
        this.listarUseCase = listarUseCase;
    }

    @PostMapping
    public ResponseEntity<CardapioResponseDTO> criarCardapio(@RequestBody @Valid CreateCardapioDTO dto, UriComponentsBuilder uriBuilder) {
        var response = criarUseCase.criar(dto);
        URI location = uriBuilder.path("/api/v1/cardapios/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<Page<CardapioResponseDTO>> listarCardapios(@PathVariable Long restauranteId, @PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        var page = listarUseCase.listar(restauranteId, pageable);
        return ResponseEntity.ok(page);
    }
}