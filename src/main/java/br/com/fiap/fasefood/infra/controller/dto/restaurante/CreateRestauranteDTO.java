package br.com.fiap.fasefood.infra.controller.dto.restaurante;

import br.com.fiap.fasefood.infra.controller.dto.EnderecoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

public record CreateRestauranteDTO(
        @NotBlank(message = "O nome é obrigatório")
        String nome,

        @NotNull(message = "O endereço é obrigatório")
        @Valid
        EnderecoDTO endereco,

        @NotBlank(message = "O tipo de cozinha é obrigatório")
        String tipoCozinha,

        @NotNull(message = "O horário de abertura é obrigatório")
        LocalTime horarioAbertura,

        @NotNull(message = "O horário de fechamento é obrigatório")
        LocalTime horarioFechamento,

        @NotNull(message = "O ID do dono é obrigatório")
        Long donoId
) {}