package br.com.fiap.fasefood.infra.controllers.docs;

import br.com.fiap.fasefood.infra.controllers.dto.CreateUserDTO;
import br.com.fiap.fasefood.infra.controllers.dto.ListUserDTO;
import br.com.fiap.fasefood.infra.controllers.dto.UpdateUserDataDTO;
import br.com.fiap.fasefood.infra.controllers.dto.UpdateUserTypeDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

@Tag(name = "Usuários", description = "Controller para crud de usuários")
public interface UsuarioControllerDocs {

    @Operation(
            summary = "Listar usuários",
            description = "Lista todos os usuários ativos com paginação",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuários listados com sucesso")
            }
    )
    ResponseEntity<Page<ListUserDTO>> listarTodos(Pageable pageable);

    @Operation(
            summary = "Buscar usuário por ID",
            description = "Busca os dados de um usuário pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            }
    )
    ResponseEntity<ListUserDTO> buscarUsuarioPorId(Long id);

    @Operation(
            summary = "Cadastrar um novo usuário",
            description = "Cadastrar um novo usuário no sistema",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Criado")
            }
    )
    ResponseEntity<ListUserDTO> saveUser(
            @Parameter(description = "Dados para criação do usuário", required = true) CreateUserDTO createUserDTO,
            UriComponentsBuilder uriBuilder
    );

    @Operation(
            summary = "Atualizar usuário",
            description = "Atualiza os dados de um usuário com base no ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            }
    )
    ResponseEntity<ListUserDTO> atualizarUsuario(
            @Parameter(description = "ID do usuário") Long id,
            @Parameter(description = "Dados para atualização") UpdateUserDataDTO dados
    );

    @Operation(
            summary = "Deletar usuário",
            description = "Desativa um usuário pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Usuário desativado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            }
    )
    ResponseEntity<Void> deletarUsuario(@Parameter(description = "ID do usuário") Long id);

    @Operation(
            summary = "Alterar tipo de um usuário",
            description = "Altera o tipo de um usuário (ex: de CLIENTE para DONO_RESTAURANTE).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tipo de usuário alterado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Usuário ou Tipo de usuário não encontrado")
            }
    )
    ResponseEntity<ListUserDTO> alterarTipoUsuario(
            @Parameter(description = "ID do usuário a ser alterado", required = true) Long id,
            @Parameter(description = "Novo tipo de usuário", required = true) UpdateUserTypeDTO updateUserTypeDTO
    );
}