package br.com.fiap.fasefood.controllers;

import br.com.fiap.fasefood.dtos.*;
import br.com.fiap.fasefood.services.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public ResponseEntity<Page<ListUserDTO>> findAll(
            @Parameter(description = "Parâmetros de paginação")
            @PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var users = this.userService.findAllUsers(paginacao);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListUserDTO> findUser(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable("id") Long id) {
        return this.userService.findUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ListUserDTO> saveUser(
            @Parameter(description = "Dados para criação do usuário", required = true)
            @RequestBody @Valid CreateUserDTO createUserDTO,
            UriComponentsBuilder uriBuilder) {
        var savedUser = this.userService.saveUser(createUserDTO);
        URI location = uriBuilder.path("/api/v1/users/{id}")
                .buildAndExpand(savedUser.id()).toUri();
        return ResponseEntity.created(location).body(savedUser);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ListUserDTO> updateUser(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable Long id,
            @Parameter(description = "Dados para atualização do usuário", required = true)
            @RequestBody @Valid UpdateUserDataDTO userData) {
        if (!id.equals(userData.id())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        var updatedUser = this.userService.updateUserDetails(userData);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/{id}/password")
    @Transactional
    public ResponseEntity<Void> changeUserPassword(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable Long id,
            @Parameter(description = "Nova senha do usuário", required = true)
            @RequestBody @Valid ChangeUserPasswordDTO userData) {

        this.userService.changeUserPassword(id, userData);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable("id") Long id) {
        boolean deleted = this.userService.deleteUser(id);
        return deleted ? ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Parameter(description = "Credenciais do usuário", required = true)
            @RequestBody @Valid LoginRequestDTO loginRequestDTO) {

        LoginResponseDTO response = userService.authenticate(loginRequestDTO);
        return ResponseEntity.ok(response);
    }

}