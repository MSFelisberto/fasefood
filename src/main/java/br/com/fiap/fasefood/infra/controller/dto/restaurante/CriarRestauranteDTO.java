package br.com.fiap.fasefood.infra.controller.dto.restaurante;

import br.com.fiap.fasefood.core.domain.enums.TipoCozinha;
import br.com.fiap.fasefood.infra.controller.dto.EnderecoDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record CriarRestauranteDTO(
        @NotBlank(message = "O nome é obrigatório")
        String nome,
        EnderecoDTO endereco,
        TipoCozinha tipoCozinha,
        LocalDateTime horarioFuncionamento,

        @NotBlank(message = "O email é obrigatório")
        @Email(message = "Formato de email inválido")
        String emailUsuario
) {
}
