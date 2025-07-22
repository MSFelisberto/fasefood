package br.com.fiap.fasefood.infra.controller;

import br.com.fiap.fasefood.core.domain.entities.Cardapio;
import br.com.fiap.fasefood.core.usecase.interfaces.cardapio.AtualizarCardapioUseCase;
import br.com.fiap.fasefood.core.usecase.interfaces.cardapio.BuscarCardapioPorIdUseCase;
import br.com.fiap.fasefood.core.usecase.interfaces.cardapio.CriarCardapioUseCase;
import br.com.fiap.fasefood.core.usecase.interfaces.cardapio.DeletarCardapioUseCase;
import br.com.fiap.fasefood.core.usecase.interfaces.cardapio.ListarTodosCardapioUseCase;
import br.com.fiap.fasefood.infra.controller.dto.CreateCardapioDTO;
import br.com.fiap.fasefood.infra.controller.dto.ListCardapioDTO;
import br.com.fiap.fasefood.infra.controller.dto.UpdateCardapioDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Tag(name = "Cardápio", description = "Controller para crud do cardápio")
@RestController
@RequestMapping("/api/v1/cardapio")
public class CardapioController {

    private final CriarCardapioUseCase criarCardapioUseCase;
    private final AtualizarCardapioUseCase atualizarCardapioUseCase;
    private final DeletarCardapioUseCase deletarCardapioUseCase;
    private final BuscarCardapioPorIdUseCase buscarCardapioPorIdUseCase;
    private final ListarTodosCardapioUseCase listarTodosCardapioUseCase;

    public CardapioController(
            CriarCardapioUseCase criarCardapioUseCase,
            AtualizarCardapioUseCase atualizarCardapioUseCase,
            DeletarCardapioUseCase deletarCardapioUseCase,
            BuscarCardapioPorIdUseCase buscarCardapioPorIdUseCase,
            ListarTodosCardapioUseCase listarTodosCardapioUseCase) {
        this.criarCardapioUseCase = criarCardapioUseCase;
        this.atualizarCardapioUseCase = atualizarCardapioUseCase;
        this.deletarCardapioUseCase = deletarCardapioUseCase;
        this.buscarCardapioPorIdUseCase = buscarCardapioPorIdUseCase;
        this.listarTodosCardapioUseCase = listarTodosCardapioUseCase;
    }

    @Operation(
            summary = "Listar Cardápio",
            description = "Lista todo o cardápio com paginação",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cardápio listados com sucesso")
            }
    )
    @GetMapping
    public ResponseEntity<Page<ListCardapioDTO>> listarTodos(
            @Parameter(description = "Parâmetros de paginação")
            @PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {

        Page<ListCardapioDTO> cardapioDTOS = listarTodosCardapioUseCase.listar(pageable);
        return ResponseEntity.ok(cardapioDTOS);
    }

    @Operation(
            summary = "Buscar cardápio por ID",
            description = "Busca os dados de um cardápio pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cardápio encontrado"),
                    @ApiResponse(responseCode = "404", description = "Cardápio não encontrado")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ListCardapioDTO> buscarCardapioPorId(@PathVariable Long id) {
        ListCardapioDTO cardapio = buscarCardapioPorIdUseCase.buscarPorId(id);
        return ResponseEntity.ok(cardapio);
    }

    @Operation(
            summary = "Cadastrar um novo cardápio",
            description = "Cadastrar um novo cardápio no sistema",
            responses = {
                    @ApiResponse(description = "Criado", responseCode = "201")
            }
    )
    @PostMapping
    @Transactional
    public ResponseEntity<ListCardapioDTO> savedCardapio(
            @Parameter(description = "Dados para criação do cardápio", required = true)
            @RequestBody @Valid CreateCardapioDTO createCardapioDTO,
            UriComponentsBuilder uriBuilder
    ) {
        Cardapio savedCardapio = criarCardapioUseCase.criarCardapio(createCardapioDTO);
        ListCardapioDTO response = new ListCardapioDTO(savedCardapio);

        URI location = uriBuilder.path("/api/v1/users/{id}")
                .buildAndExpand(savedCardapio.getId()).toUri();

        return ResponseEntity.created(location).body(response);
    }

    @Operation(
            summary = "Atualizar cardápio",
            description = "Atualiza os dados de um cardápio com base no ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "cardápio atualizado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "cardápio não encontrado")
            }
    )
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ListCardapioDTO> atualizarCardapio(
            @PathVariable Long id,
            @RequestBody @Valid UpdateCardapioDTO dados) {
        ListCardapioDTO atualizado = atualizarCardapioUseCase.atualizar(id, dados);
        return ResponseEntity.ok(atualizado);
    }

    @Operation(
            summary = "Deletar cardápio",
            description = "Desativa um cardápio pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Cardápio desativado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Cardápio não encontrado")
            }
    )
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        deletarCardapioUseCase.deletar(id);
        return ResponseEntity.noContent().build();
    }
}