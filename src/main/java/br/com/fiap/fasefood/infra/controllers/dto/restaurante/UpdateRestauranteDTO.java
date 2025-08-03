package br.com.fiap.fasefood.infra.controllers.dto.restaurante;

import br.com.fiap.fasefood.infra.controllers.dto.usuario.EnderecoDTO;
import jakarta.validation.Valid;
import java.time.LocalTime;

public record UpdateRestauranteDTO(
        String nome,
        @Valid
        EnderecoDTO endereco,
        String tipoCozinha,
        LocalTime horarioAbertura,
        LocalTime horarioFechamento
) {}