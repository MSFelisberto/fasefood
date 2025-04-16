package br.com.fiap.fasefood.dtos;

import br.com.fiap.fasefood.entities.Endereco;
import br.com.fiap.fasefood.enums.ETipoUsuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUserDTO(
        @NotBlank(message = "O nome é obrigatório")
        String nome,

        @NotBlank(message = "O email é obrigatório")
        @Email(message = "Formato de email inválido")
        String email,

        @NotBlank(message = "O login é obrigatório")
        String login,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
        String senha,

        @NotNull(message = "O tipo de usuário é obrigatório")
        ETipoUsuario tipoUsuario,

        @NotNull(message = "O endereço é obrigatório")
        @Valid
        EnderecoDTO endereco
) {
}
