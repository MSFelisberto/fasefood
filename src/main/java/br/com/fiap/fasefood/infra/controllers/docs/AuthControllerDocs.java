package br.com.fiap.fasefood.infra.controllers.docs;

import br.com.fiap.fasefood.infra.controllers.dto.ChangeUserPasswordDTO;
import br.com.fiap.fasefood.infra.controllers.dto.LoginRequestDTO;
import br.com.fiap.fasefood.infra.controllers.dto.LoginResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Autenticação", description = "Endpoints de autenticação de usuários")
public interface AuthControllerDocs {

    @Operation(
            summary = "Realizar login",
            description = "Autentica o usuário com login e senha válidos",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
            }
    )
    ResponseEntity<LoginResponseDTO> login(
            @Parameter(description = "Credenciais de login", required = true)
            LoginRequestDTO loginRequest
    );

    @Operation(
            summary = "Alterar senha do usuário",
            description = "Altera a senha de um usuário ativo pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            }
    )
    ResponseEntity<Void> alterarSenha(
            @Parameter(description = "ID do usuário", required = true) Long id,
            @Parameter(description = "Nova senha", required = true)
            ChangeUserPasswordDTO senha
    );
}