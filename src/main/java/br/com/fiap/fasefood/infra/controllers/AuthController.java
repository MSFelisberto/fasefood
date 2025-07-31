package br.com.fiap.fasefood.infra.controllers;

import br.com.fiap.fasefood.application.usecases.autenticacao.atualizar.AlterarSenhaUsuarioUseCase;
import br.com.fiap.fasefood.application.usecases.autenticacao.autenticar.AutenticarUsuarioUseCase;
import br.com.fiap.fasefood.infra.controllers.docs.AuthControllerDocs;
import br.com.fiap.fasefood.infra.controllers.dto.ChangeUserPasswordDTO;
import br.com.fiap.fasefood.infra.controllers.dto.LoginRequestDTO;
import br.com.fiap.fasefood.infra.controllers.dto.LoginResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticação", description = "Endpoints de autenticação de usuários")
public class AuthController implements AuthControllerDocs {
    private final AutenticarUsuarioUseCase autenticarUseCase;
    private final AlterarSenhaUsuarioUseCase alterarSenhaUseCase;

    public AuthController(
            AutenticarUsuarioUseCase autenticarUseCase,
            AlterarSenhaUsuarioUseCase alterarSenhaUseCase
    ) {
        this.autenticarUseCase = autenticarUseCase;
        this.alterarSenhaUseCase = alterarSenhaUseCase;
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequest) {
        return ResponseEntity.ok(autenticarUseCase.autenticar(loginRequest));
    }

    @Override
    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> alterarSenha(
            @PathVariable Long id,
            @RequestBody @Valid ChangeUserPasswordDTO senha
    ) {
        alterarSenhaUseCase.alterarSenha(id, senha);
        return ResponseEntity.noContent().build();
    }
}
