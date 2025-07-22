package br.com.fiap.fasefood.infra.controller;

import br.com.fiap.fasefood.core.usecase.interfaces.usuario.AlterarSenhaUsuarioUseCase;
import br.com.fiap.fasefood.core.usecase.interfaces.usuario.AutenticarUsuarioUseCase;
import br.com.fiap.fasefood.infra.controller.dto.ChangeUserPasswordDTO;
import br.com.fiap.fasefood.infra.controller.dto.LoginRequestDTO;
import br.com.fiap.fasefood.infra.controller.dto.LoginResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticação", description = "Endpoints de autenticação de usuários")
public class AuthController {
    private final AutenticarUsuarioUseCase autenticarUseCase;
    private final AlterarSenhaUsuarioUseCase alterarSenhaUseCase;

    public AuthController(
            AutenticarUsuarioUseCase autenticarUseCase,
            AlterarSenhaUsuarioUseCase alterarSenhaUseCase
    ) {
        this.autenticarUseCase = autenticarUseCase;
        this.alterarSenhaUseCase = alterarSenhaUseCase;
    }

    @Operation(
            summary = "Realizar login",
            description = "Autentica o usuário com login e senha válidos",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Parameter(description = "Credenciais de login", required = true)
            @RequestBody @Valid LoginRequestDTO loginRequest
    ) {
        return ResponseEntity.ok(autenticarUseCase.autenticar(loginRequest));
    }

    @Operation(
            summary = "Alterar senha do usuário",
            description = "Altera a senha de um usuário ativo pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            }
    )
    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> alterarSenha(
            @Parameter(description = "ID do usuário", required = true) @PathVariable Long id,
            @Parameter(description = "Nova senha", required = true)
            @RequestBody @Valid ChangeUserPasswordDTO senha
    ) {
        alterarSenhaUseCase.alterarSenha(id, senha);
        return ResponseEntity.noContent().build();
    }
}
