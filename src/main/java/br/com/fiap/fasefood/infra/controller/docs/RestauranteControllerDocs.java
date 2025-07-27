package br.com.fiap.fasefood.infra.controller.docs;

import br.com.fiap.fasefood.infra.controller.dto.restaurante.CreateRestauranteDTO;
import br.com.fiap.fasefood.infra.controller.dto.restaurante.RestauranteResponseDTO;
import br.com.fiap.fasefood.infra.controller.dto.restaurante.UpdateRestauranteDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

@Tag(name = "Restaurantes", description = "Endpoints para gerenciamento de restaurantes")
public interface RestauranteControllerDocs {

    @Operation(
            summary = "Criar um novo restaurante",
            description = "Cadastra um novo restaurante no sistema.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Restaurante criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos ou violação de regra de negócio")
            }
    )
    ResponseEntity<RestauranteResponseDTO> criarRestaurante(
            @Parameter(description = "Dados para criação do restaurante") CreateRestauranteDTO dto,
            UriComponentsBuilder uriBuilder
    );

    @Operation(
            summary = "Listar restaurantes",
            description = "Lista todos os restaurantes ativos com paginação.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Restaurantes listados com sucesso")
            }
    )
    ResponseEntity<Page<RestauranteResponseDTO>> listarRestaurantes(Pageable pageable);

    @Operation(
            summary = "Buscar restaurante por ID",
            description = "Busca os dados de um restaurante pelo seu ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Restaurante encontrado"),
                    @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
            }
    )
    ResponseEntity<RestauranteResponseDTO> buscarRestaurantePorId(
            @Parameter(description = "ID do restaurante") Long id
    );

    @Operation(
            summary = "Atualizar um restaurante",
            description = "Atualiza os dados de um restaurante existente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Restaurante atualizado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
            }
    )
    ResponseEntity<RestauranteResponseDTO> atualizarRestaurante(
            @Parameter(description = "ID do restaurante") Long id,
            @Parameter(description = "Novos dados do restaurante") UpdateRestauranteDTO dto
    );

    @Operation(
            summary = "Deletar um restaurante",
            description = "Desativa um restaurante do sistema (soft delete).",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Restaurante desativado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
            }
    )
    ResponseEntity<Void> deletarRestaurante(
            @Parameter(description = "ID do restaurante") Long id
    );
}