package br.com.fiap.fasefood.controllers;

import br.com.fiap.fasefood.dtos.CreateUserDTO;
import br.com.fiap.fasefood.dtos.ListUserDTO;
import br.com.fiap.fasefood.dtos.UpdateUserDataDTO;
import br.com.fiap.fasefood.entities.User;
import br.com.fiap.fasefood.repositories.UserRepository;
import br.com.fiap.fasefood.services.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("users")

public class UserController {

    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public ResponseEntity<Page<ListUserDTO>> findAll(@PageableDefault(size = 10, sort = {"id"})  Pageable paginacao) {
        var users = this.userService.findAllUsers(paginacao);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ListUserDTO>> findUser(@PathVariable("id") Long id) {
        var user = this.userService.findUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> saveUser(@RequestBody @Valid CreateUserDTO createUserDTO) {
        this.userService.saveUser(createUserDTO);
        return ResponseEntity.status(201).build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Void> updateUser(@RequestBody @Valid UpdateUserDataDTO userData) {
        this.userService.updateUser(userData);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{Id}")
    @Transactional
    public ResponseEntity<Void> deleteUser(@PathVariable("Id") Long Id) {
        this.userService.deleteUser(Id);
        return ResponseEntity.noContent().build();
    }


}
