package br.com.fiap.fasefood.infra.controllers;

import br.com.fiap.fasefood.application.usecases.cardapio.atualizar.AtualizarCardapioItemUseCase;
import br.com.fiap.fasefood.application.usecases.cardapio.atualizar.AtualizarCardapioItensBatchUseCase;
import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioItemUseCase;
import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioItemsBatchUseCase;
import br.com.fiap.fasefood.application.usecases.cardapio.deletar.RemoverCardapioItemUseCase;
import br.com.fiap.fasefood.application.usecases.cardapio.deletar.RemoverItensCardapioUseCase;
import br.com.fiap.fasefood.application.usecases.cardapio.listar.ListarCardapioItensUseCase;
import br.com.fiap.fasefood.infra.controllers.docs.CardapioItemControllerDocs;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.*;
import br.com.fiap.fasefood.infra.controllers.mapper.cardapio.CardapioItemMapper;
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
@RequestMapping("/api/v1/item-cardapio")
public class CardapioItemController implements CardapioItemControllerDocs {

    private final CriarCardapioItemUseCase criarUseCase;
    private final ListarCardapioItensUseCase listarUseCase;
    private final AtualizarCardapioItemUseCase atualizarUseCase;
    private final RemoverCardapioItemUseCase removerUseCase;
    private final RemoverItensCardapioUseCase removerItensUseCase;
    private final CriarCardapioItemsBatchUseCase criarEmLoteUseCase;
    private final AtualizarCardapioItensBatchUseCase atualizarEmLoteUseCase;

    public CardapioItemController(CriarCardapioItemUseCase criarUseCase,
                                  ListarCardapioItensUseCase listarUseCase,
                                  AtualizarCardapioItemUseCase atualizarUseCase,
                                  RemoverCardapioItemUseCase removerUseCase,
                                  RemoverItensCardapioUseCase removerItensUseCase,
                                  CriarCardapioItemsBatchUseCase criarEmLoteUseCase,
                                  AtualizarCardapioItensBatchUseCase atualizarEmLoteUseCase) {
        this.criarUseCase = criarUseCase;
        this.listarUseCase = listarUseCase;
        this.atualizarUseCase = atualizarUseCase;
        this.removerUseCase = removerUseCase;
        this.removerItensUseCase = removerItensUseCase;
        this.criarEmLoteUseCase = criarEmLoteUseCase;
        this.atualizarEmLoteUseCase = atualizarEmLoteUseCase;
    }

    @Override
    @PostMapping
    public ResponseEntity<CardapioItemResponseDTO> criarItem(@RequestBody @Valid CreateCardapioItemDTO dto,
                                                             UriComponentsBuilder uriBuilder) {
        var response = criarUseCase.criar(CardapioItemMapper.toCriarCardapioItemInput(dto));
        URI location = uriBuilder.path("/api/v1/item-cardapio/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(CardapioItemMapper.toCardapioItemResponseDTO(response));
    }

    @Override
    @PostMapping("/itens/batch")
    public ResponseEntity<List<CardapioItemResponseDTO>> criarItensEmLote(
            @RequestBody @Valid CreateCardapioItemsBatchDTO dto)
    {
        var inputs = CardapioItemMapper.toCriarCardapioItemsBatchInput(dto);
        var outputs = criarEmLoteUseCase.criarEmLote(inputs);

        List<CardapioItemResponseDTO> response = CardapioItemMapper.toResponseDTOList(outputs);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @GetMapping("/cardapio/{cardapioId}")
    public ResponseEntity<Page<CardapioItemResponseDTO>> listarItens(@PathVariable Long cardapioId,
                                                                     @PageableDefault(size = 10, sort = {"nome"}) Pageable pageable)
    {
        var paginacaoInput = CardapioItemMapper.toPaginationInput(pageable);
        var outputPage = listarUseCase.listar(cardapioId, paginacaoInput);
        var responsePage = CardapioItemMapper.toCardapioItemResponseDTOPage(outputPage);

        return ResponseEntity.ok(responsePage);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<CardapioItemResponseDTO> atualizarItem(@PathVariable Long id,
                                                                 @RequestBody @Valid UpdateCardapioItemDTO dto)
    {
        var input = CardapioItemMapper.toAtualizarCardapioItemInput(dto);
        var output = atualizarUseCase.atualizar(id, input);
        var response = CardapioItemMapper.toCardapioItemResponseDTO(output);
        return ResponseEntity.ok(response);
    }

    @Override
    @PutMapping("/itens/batch")
    public ResponseEntity<List<CardapioItemResponseDTO>> atualizarItensEmLote(
            @RequestBody @Valid UpdateCardapioItemsBatchDTO dto)
    {
        var inputs = CardapioItemMapper.toAtualizarCardapioItemBatchInputList(dto);
        var outputs = atualizarEmLoteUseCase.atualizarEmLote(inputs);
        var response = CardapioItemMapper.toResponseDTOList(outputs);
        return ResponseEntity.ok(response);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerItem(@PathVariable Long id) {
        removerUseCase.remover(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/itens/batch")
    public ResponseEntity<Void> removerItens(@RequestBody @Valid RemoverItensCardapioDTO dto) {
        removerItensUseCase.removerEmLote(dto.itemIds());
        return ResponseEntity.noContent().build();
    }
}