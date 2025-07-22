package br.com.fiap.fasefood.infra.controller;

import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.core.usecase.interfaces.usuario.AlterarTipoUsuarioUseCase;
import br.com.fiap.fasefood.core.usecase.interfaces.usuario.AtualizarUsuarioUseCase;
import br.com.fiap.fasefood.core.usecase.interfaces.usuario.BuscarUsuarioPorIdUseCase;
import br.com.fiap.fasefood.core.usecase.interfaces.usuario.CriarUsuarioUseCase;
import br.com.fiap.fasefood.core.usecase.interfaces.usuario.DeletarUsuarioUseCase;
import br.com.fiap.fasefood.core.usecase.interfaces.usuario.ListarTodosUsuariosUseCase;
import br.com.fiap.fasefood.infra.controller.dto.CreateUserDTO;
import br.com.fiap.fasefood.infra.controller.dto.ListUserDTO;
import br.com.fiap.fasefood.infra.controller.dto.UpdateUserDataDTO;
import br.com.fiap.fasefood.infra.controller.dto.UpdateUserTypeDTO;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Tag(name = "Usuários", description = "Controller para crud de usuários")
@RestController
@RequestMapping("/api/v1/users")
public class ItemCardapioController {

    private final CriarUsuarioUseCase criarUsuarioUseCase;
    private final AtualizarUsuarioUseCase atualizarUsuarioUseCase;
    private final DeletarUsuarioUseCase deletarUsuarioUseCase;
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private final ListarTodosUsuariosUseCase listarTodosUsuariosUseCase;
    private final AlterarTipoUsuarioUseCase alterarTipoUsuarioUseCase;

    public ItemCardapioController(
            CriarUsuarioUseCase criarUsuarioUseCase,
            AtualizarUsuarioUseCase atualizarUsuarioUseCase,
            DeletarUsuarioUseCase deletarUsuarioUseCase,
            BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase,
            ListarTodosUsuariosUseCase listarTodosUsuariosUseCase,
            AlterarTipoUsuarioUseCase alterarTipoUsuarioUseCase) {
        this.criarUsuarioUseCase = criarUsuarioUseCase;
        this.atualizarUsuarioUseCase = atualizarUsuarioUseCase;
        this.deletarUsuarioUseCase = deletarUsuarioUseCase;
        this.buscarUsuarioPorIdUseCase = buscarUsuarioPorIdUseCase;
        this.listarTodosUsuariosUseCase = listarTodosUsuariosUseCase;
        this.alterarTipoUsuarioUseCase = alterarTipoUsuarioUseCase;
    }

    @Operation(
            summary = "Listar usuários",
            description = "Lista todos os usuários ativos com paginação",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuários listados com sucesso")
            }
    )
    @GetMapping
    public ResponseEntity<Page<ListUserDTO>> listarTodos(
            @Parameter(description = "Parâmetros de paginação")
            @PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {

        Page<ListUserDTO> usuarios = listarTodosUsuariosUseCase.listar(pageable);
        return ResponseEntity.ok(usuarios);
    }

    @Operation(
            summary = "Buscar usuário por ID",
            description = "Busca os dados de um usuário pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ListUserDTO> buscarUsuarioPorId(@PathVariable Long id) {
        ListUserDTO usuario = buscarUsuarioPorIdUseCase.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @Operation(
            summary = "Cadastrar um novo usuário",
            description = "Cadastrar um novo usuário no sistema",
            responses = {
                    @ApiResponse(description = "Criado", responseCode = "201")
            }
    )
    @PostMapping
    @Transactional
    public ResponseEntity<ListUserDTO> saveUser(
            @Parameter(description = "Dados para criação do usuário", required = true)
            @RequestBody @Valid CreateUserDTO createUserDTO,
            UriComponentsBuilder uriBuilder
    ) {
        Usuario savedUser = criarUsuarioUseCase.criarUsuario(createUserDTO);
        ListUserDTO response = new ListUserDTO(savedUser);

        URI location = uriBuilder.path("/api/v1/users/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).body(response);
    }

    @Operation(
            summary = "Atualizar usuário",
            description = "Atualiza os dados de um usuário com base no ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            }
    )
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ListUserDTO> atualizarUsuario(
            @PathVariable Long id,
            @RequestBody @Valid UpdateUserDataDTO dados) {
        ListUserDTO atualizado = atualizarUsuarioUseCase.atualizar(id, dados);
        return ResponseEntity.ok(atualizado);
    }

    @Operation(
            summary = "Deletar usuário",
            description = "Desativa um usuário pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Usuário desativado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            }
    )
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        deletarUsuarioUseCase.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/tipo")
    @Transactional
    public ResponseEntity<ListUserDTO> alterarTipoUsuario(
            @Parameter(description = "ID do usuário a ser alterado", required = true)
            @PathVariable Long id,
            @Parameter(description = "ID do novo tipo de usuario", required = true)
            @RequestBody @Valid UpdateUserTypeDTO updateUserTypeDTO
            ) {
        ListUserDTO usuarioAtualizado = alterarTipoUsuarioUseCase.alterarTipoUsuario(id, updateUserTypeDTO);
        return ResponseEntity.ok(usuarioAtualizado);
    }
}