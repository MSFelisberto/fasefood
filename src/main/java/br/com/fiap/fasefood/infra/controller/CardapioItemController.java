package br.com.fiap.fasefood.infra.controller;

import br.com.fiap.fasefood.core.usecase.interfaces.cardapio.*;
import br.com.fiap.fasefood.infra.controller.docs.CardapioItemControllerDocs;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CardapioItemResponseDTO;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CreateCardapioItemDTO;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.CreateCardapioItemsBatchDTO;
import br.com.fiap.fasefood.infra.controller.dto.cardapio.UpdateCardapioItemDTO;
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

    public CardapioItemController(CriarCardapioItemUseCase criarUseCase, ListarCardapioItensUseCase listarUseCase, AtualizarCardapioItemUseCase atualizarUseCase, DeletarCardapioItemUseCase deletarUseCase, CriarCardapioItemsBatchUseCase criarEmLoteUseCase) {
        this.criarUseCase = criarUseCase;
        this.listarUseCase = listarUseCase;
        this.atualizarUseCase = atualizarUseCase;
        this.deletarUseCase = deletarUseCase;
        this.criarEmLoteUseCase = criarEmLoteUseCase;
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarItem(@PathVariable Long id) {
        deletarUseCase.deletar(id);
        return ResponseEntity.noContent().build();
    }
}