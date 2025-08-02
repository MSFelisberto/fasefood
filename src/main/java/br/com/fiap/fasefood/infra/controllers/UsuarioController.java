package br.com.fiap.fasefood.infra.controllers;

import br.com.fiap.fasefood.application.usecases.usuario.alterar.AlterarTipoUsuarioUseCase;
import br.com.fiap.fasefood.application.usecases.usuario.alterar.AtualizarUsuarioUseCase;
import br.com.fiap.fasefood.application.usecases.usuario.criar.CriarUsuarioOutput;
import br.com.fiap.fasefood.application.usecases.usuario.criar.CriarUsuarioUseCase;
import br.com.fiap.fasefood.application.usecases.usuario.deletar.DeletarUsuarioUseCase;
import br.com.fiap.fasefood.application.usecases.usuario.listar.BuscarUsuarioPorIdUseCase;
import br.com.fiap.fasefood.application.usecases.usuario.listar.ListUserOutput;
import br.com.fiap.fasefood.application.usecases.usuario.listar.ListarTodosUsuariosUseCase;
import br.com.fiap.fasefood.infra.controllers.docs.UsuarioControllerDocs;
import br.com.fiap.fasefood.infra.controllers.dto.usuario.CreateUserDTO;
import br.com.fiap.fasefood.infra.controllers.dto.usuario.ListUserDTO;
import br.com.fiap.fasefood.infra.controllers.dto.usuario.UpdateUserDataDTO;
import br.com.fiap.fasefood.infra.controllers.dto.usuario.UpdateUserTypeDTO;
import br.com.fiap.fasefood.infra.controllers.mapper.usuario.ListUserMapper;
import br.com.fiap.fasefood.infra.controllers.mapper.usuario.UsuarioMapper;
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
            AlterarTipoUsuarioUseCase alterarTipoUsuarioUseCase
    ) {
        this.criarUsuarioUseCase = criarUsuarioUseCase;
        this.atualizarUsuarioUseCase = atualizarUsuarioUseCase;
        this.deletarUsuarioUseCase = deletarUsuarioUseCase;
        this.buscarUsuarioPorIdUseCase = buscarUsuarioPorIdUseCase;
        this.listarTodosUsuariosUseCase = listarTodosUsuariosUseCase;
        this.alterarTipoUsuarioUseCase = alterarTipoUsuarioUseCase;
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<ListUserDTO>> listarTodos(
            @PageableDefault(size = 10, sort = {"nome"}) Pageable pageable
    ) {
        Page<ListUserOutput> usuarios = listarTodosUsuariosUseCase.listar(pageable);
        return ResponseEntity.ok(UsuarioMapper.toListUserOutputPaginacao(usuarios));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ListUserDTO> buscarUsuarioPorId(@PathVariable Long id) {
        ListUserOutput usuario = buscarUsuarioPorIdUseCase.buscarPorId(id);
        return ResponseEntity.ok(ListUserMapper.toDTO(usuario));
    }

    @Override
    @PostMapping
    @Transactional
    public ResponseEntity<ListUserDTO> saveUser(
            @RequestBody @Valid CreateUserDTO createUserDTO,
            UriComponentsBuilder uriBuilder
    ) {
        CriarUsuarioOutput savedUser = criarUsuarioUseCase.criarUsuario(
                UsuarioMapper.toCriarUsuarioInput(createUserDTO)
        );
        ListUserDTO response = UsuarioMapper.toListUserDTO(savedUser);
        URI location = uriBuilder.path("/api/v1/users/{id}").buildAndExpand(savedUser.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Override
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ListUserDTO> atualizarUsuario(
            @PathVariable Long id,
            @RequestBody @Valid UpdateUserDataDTO dados
    ) {
        ListUserOutput atualizado = atualizarUsuarioUseCase.atualizar(
                id,
                UsuarioMapper.toUpdateUserDataInput(dados)
        );
        return ResponseEntity.ok(ListUserMapper.toDTO(atualizado));
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
    public ResponseEntity<ListUserDTO> alterarTipoUsuario(
            @PathVariable Long id,
            @RequestBody @Valid UpdateUserTypeDTO updateUserTypeDTO
    ) {
        ListUserOutput usuarioAtualizado = alterarTipoUsuarioUseCase.alterarTipoUsuario(
                id,
                UsuarioMapper.toUpdateUserTypeInput(updateUserTypeDTO)
        );
        return ResponseEntity.ok(UsuarioMapper.toListUserDTO(usuarioAtualizado));
    }
}