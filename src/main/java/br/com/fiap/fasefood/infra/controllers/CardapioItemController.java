package br.com.fiap.fasefood.infra.controllers;

import br.com.fiap.fasefood.application.usecases.cardapio.atualizar.AtualizarCardapioItemUseCase;
import br.com.fiap.fasefood.application.usecases.cardapio.atualizar.AtualizarCardapioItensBatchUseCase;
import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioItemUseCase;
import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioItemsBatchUseCase;
import br.com.fiap.fasefood.application.usecases.cardapio.deletar.DeletarCardapioItemUseCase;
import br.com.fiap.fasefood.application.usecases.cardapio.listar.ListarCardapioItensUseCase;
import br.com.fiap.fasefood.infra.controllers.docs.CardapioItemControllerDocs;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cardapio")
public class CardapioItemController implements CardapioItemControllerDocs {

    private final CriarCardapioItemUseCase criarUseCase;
    private final ListarCardapioItensUseCase listarUseCase;
    private final AtualizarCardapioItemUseCase atualizarUseCase;
    private final DeletarCardapioItemUseCase deletarUseCase;
    private final CriarCardapioItemsBatchUseCase criarEmLoteUseCase;
    private final AtualizarCardapioItensBatchUseCase atualizarEmLoteUseCase;

    public CardapioItemController(CriarCardapioItemUseCase criarUseCase, ListarCardapioItensUseCase listarUseCase, AtualizarCardapioItemUseCase atualizarUseCase, DeletarCardapioItemUseCase deletarUseCase, CriarCardapioItemsBatchUseCase criarEmLoteUseCase, AtualizarCardapioItensBatchUseCase atualizarEmLoteUseCase) {
        this.criarUseCase = criarUseCase;
        this.listarUseCase = listarUseCase;
        this.atualizarUseCase = atualizarUseCase;
        this.deletarUseCase = deletarUseCase;
        this.criarEmLoteUseCase = criarEmLoteUseCase;
        this.atualizarEmLoteUseCase = atualizarEmLoteUseCase;
    }

    @Override
    @PostMapping
    public ResponseEntity<CardapioItemResponseDTO> criarItem(@RequestBody @Valid CreateCardapioItemDTO dto, UriComponentsBuilder uriBuilder) {
        var response = criarUseCase.criar(dto);
        URI location = uriBuilder.path("/api/v1/cardapio/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Override
    @PostMapping("/itens/batch")
    public ResponseEntity<List<CardapioItemResponseDTO>> criarItensEmLote(@RequestBody @Valid CreateCardapioItemsBatchDTO dto) {
        List<CardapioItemResponseDTO> response = criarEmLoteUseCase.criarEmLote(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @GetMapping("/cardapio/{cardapioId}")
    public ResponseEntity<Page<CardapioItemResponseDTO>> listarItens(@PathVariable Long cardapioId, @PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        var page = listarUseCase.listar(cardapioId, pageable);
        return ResponseEntity.ok(page);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<CardapioItemResponseDTO> atualizarItem(@PathVariable Long id, @RequestBody @Valid UpdateCardapioItemDTO dto) {
        var response = atualizarUseCase.atualizar(id, dto);
        return ResponseEntity.ok(response);
    }

    @Override
    @PutMapping("/itens/batch")
    public ResponseEntity<List<CardapioItemResponseDTO>> atualizarItensEmLote(@RequestBody @Valid UpdateCardapioItemsBatchDTO dto) {
        List<CardapioItemResponseDTO> response = atualizarEmLoteUseCase.atualizarEmLote(dto);
        return ResponseEntity.ok(response);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarItem(@PathVariable Long id) {
        deletarUseCase.deletar(id);
        return ResponseEntity.noContent().build();
    }
}