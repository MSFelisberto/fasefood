package br.com.fiap.fasefood.infra.controller;

import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.core.usecase.interfaces.*;
import br.com.fiap.fasefood.infra.controller.docs.UsuarioControllerDocs;
import br.com.fiap.fasefood.infra.controller.dto.CreateUserDTO;
import br.com.fiap.fasefood.infra.controller.dto.ListUserDTO;
import br.com.fiap.fasefood.infra.controller.dto.UpdateUserDataDTO;
import br.com.fiap.fasefood.infra.controller.dto.UpdateUserTypeDTO;
import br.com.fiap.fasefood.infra.controller.mapper.UsuarioMapper;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Tag(name = "Usuários", description = "Controller para crud de usuários")
@RestController
@RequestMapping("/api/v1/users")
public class UsuarioController implements UsuarioControllerDocs {

    private final CriarUsuarioUseCase criarUsuarioUseCase;
    private final AtualizarUsuarioUseCase atualizarUsuarioUseCase;
    private final DeletarUsuarioUseCase deletarUsuarioUseCase;
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private final ListarTodosUsuariosUseCase listarTodosUsuariosUseCase;
    private final AlterarTipoUsuarioUseCase alterarTipoUsuarioUseCase;

    public UsuarioController(
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

    @Override
    @GetMapping
    public ResponseEntity<Page<ListUserDTO>> listarTodos(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        Page<ListUserDTO> usuarios = listarTodosUsuariosUseCase.listar(pageable);
        return ResponseEntity.ok(usuarios);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ListUserDTO> buscarUsuarioPorId(@PathVariable Long id) {
        ListUserDTO usuario = buscarUsuarioPorIdUseCase.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @Override
    @PostMapping
    @Transactional
    public ResponseEntity<ListUserDTO> saveUser(@RequestBody @Valid CreateUserDTO createUserDTO, UriComponentsBuilder uriBuilder) {
        Usuario savedUser = criarUsuarioUseCase.criarUsuario(createUserDTO);
        ListUserDTO response = UsuarioMapper.toListUserDTO(savedUser);
        URI location = uriBuilder.path("/api/v1/users/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Override
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ListUserDTO> atualizarUsuario(@PathVariable Long id, @RequestBody @Valid UpdateUserDataDTO dados) {
        ListUserDTO atualizado = atualizarUsuarioUseCase.atualizar(id, dados);
        return ResponseEntity.ok(atualizado);
    }

    @Override
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        deletarUsuarioUseCase.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PatchMapping("/{id}/tipo")
    @Transactional
    public ResponseEntity<ListUserDTO> alterarTipoUsuario(@PathVariable Long id, @RequestBody @Valid UpdateUserTypeDTO updateUserTypeDTO) {
        ListUserDTO usuarioAtualizado = alterarTipoUsuarioUseCase.alterarTipoUsuario(id, updateUserTypeDTO);
        return ResponseEntity.ok(usuarioAtualizado);
    }
}