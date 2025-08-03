package br.com.fiap.fasefood.infra.controllers.docs;

import br.com.fiap.fasefood.infra.controllers.dto.cardapio.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Tag(name = "Itens de Cardápio", description = "Endpoints para gerenciamento de itens específicos de um cardápio")
public interface CardapioItemControllerDocs {

    @Operation(
            summary = "Criar item no cardápio",
            description = "Cadastra um novo item e o associa a um cardápio existente.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Item criado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Cardápio não encontrado")
            }
    )
    ResponseEntity<CardapioItemResponseDTO> criarItem(
            @Parameter(description = "Dados para criação do item") CreateCardapioItemDTO dto,
            UriComponentsBuilder uriBuilder
    );

    @Operation(
            summary = "Criar múltiplos itens no cardápio",
            description = "Cadastra uma lista de novos itens e os associa a um cardápio existente em uma única requisição.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Itens criados com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Cardápio não encontrado")
            }
    )
    ResponseEntity<List<CardapioItemResponseDTO>> criarItensEmLote(
            @Parameter(description = "Dados para criação dos itens em lote") CreateCardapioItemsBatchDTO dto
    );

    @Operation(
            summary = "Listar itens de um cardápio",
            description = "Lista todos os itens ativos de um cardápio específico.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Itens listados com sucesso")
            }
    )
    ResponseEntity<Page<CardapioItemResponseDTO>> listarItens(
            @Parameter(description = "ID do cardápio") Long cardapioId,
            Pageable pageable
    );

    @Operation(
            summary = "Atualizar item do cardápio",
            description = "Atualiza os dados de um item de cardápio existente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Item não encontrado")
            }
    )
    ResponseEntity<CardapioItemResponseDTO> atualizarItem(
            @Parameter(description = "ID do item a ser atualizado") Long id,
            @Parameter(description = "Novos dados do item") UpdateCardapioItemDTO dto
    );

    @Operation(
            summary = "Atualizar múltiplos itens do cardápio",
            description = "Atualiza uma lista de itens de cardápio existentes em uma única requisição.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Itens atualizados com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Um ou mais itens não foram encontrados")
            }
    )
    ResponseEntity<List<CardapioItemResponseDTO>> atualizarItensEmLote(
            @Parameter(description = "Dados para atualização dos itens em lote") UpdateCardapioItemsBatchDTO dto
    );

    @Operation(
            summary = "Deletar item do cardápio",
            description = "Desativa um item de um cardápio (soft delete).",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Item desativado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Item não encontrado")
            }
    )
    ResponseEntity<Void> removerItem(
            @Parameter(description = "ID do item a ser removido") Long id
    );

    @Operation(
            summary = "Remover múltiplos itens do cardápio",
            description = "Desativa uma lista de itens de um cardápio (soft delete) em uma única requisição.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Itens desativados com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Um ou mais itens não foram encontrados")
            }
    )
    ResponseEntity<Void> removerItens(
            @Parameter(description = "Lista de IDs dos itens a serem removidos") RemoverItensCardapioDTO dto
    );
}