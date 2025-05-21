package br.com.fiap.fasefood.controllers;

import br.com.fiap.fasefood.dtos.ChangeUserPasswordDTO;
import br.com.fiap.fasefood.dtos.LoginRequestDTO;
import br.com.fiap.fasefood.dtos.LoginResponseDTO;
import br.com.fiap.fasefood.services.AuthStrategy;
import br.com.fiap.fasefood.services.SimpleAuth;
import br.com.fiap.fasefood.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication", description = "Controller para oprerações de autenticação")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthStrategy authStrategy;

    public AuthenticationController(AuthStrategy authStrategy) {
        this.authStrategy = authStrategy;
    }

    @Operation(
            summary = "Atualizar senha de usuário",
            description = "Atualizar a senha de um usuário específico com base no seu ID",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200")
            }
    )
    @PatchMapping("/{id}/password")
    @Transactional
    public ResponseEntity<Void> changeUserPassword(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable Long id,
            @Parameter(description = "Nova senha do usuário", required = true)
            @RequestBody @Valid ChangeUserPasswordDTO userData) {

        this.authStrategy.changeUserPassword(id, userData);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Autenticação de usuário",
            description = "Autenticar um usuário no sistema",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Parameter(description = "Credenciais do usuário", required = true)
            @RequestBody @Valid LoginRequestDTO loginRequestDTO
    ) {

        LoginResponseDTO response = authStrategy.authenticate(loginRequestDTO);
        return ResponseEntity.ok(response);
    }
}
