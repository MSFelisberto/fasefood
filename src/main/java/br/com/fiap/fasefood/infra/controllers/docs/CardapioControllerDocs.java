package br.com.fiap.fasefood.infra.controllers.docs;

import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CardapioResponseDTO;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CreateCardapioDTO;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CreateCardapioResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

@Tag(name = "Cardápios", description = "Endpoints para gerenciamento dos cardápios de um restaurante")
public interface CardapioControllerDocs {

    @Operation(
            summary = "Criar um novo cardápio",
            description = "Cadastra um novo cardápio para um restaurante existente (Ex: 'Menu de Almoço', 'Menu de Jantar').",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Cardápio criado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
            }
    )
    ResponseEntity<CreateCardapioResponseDTO> criarCardapio(
            @Parameter(description = "Dados para criação do cardápio") CreateCardapioDTO dto,
            UriComponentsBuilder uriBuilder
    );

    @Operation(
            summary = "Listar cardápios de um restaurante",
            description = "Lista todos os cardápios ativos de um restaurante específico.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cardápios listados com sucesso")
            }
    )
    ResponseEntity<Page<CardapioResponseDTO>> listarCardapios(
            @Parameter(description = "ID do restaurante") Long restauranteId,
            Pageable pageable
    );
}