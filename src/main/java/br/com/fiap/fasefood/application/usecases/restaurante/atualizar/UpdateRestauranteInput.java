package br.com.fiap.fasefood.application.usecases.restaurante.atualizar;

import br.com.fiap.fasefood.application.usecases.shared.endereco.EnderecoInput;

import java.time.LocalTime;

public record UpdateRestauranteInput(
        String nome,
        EnderecoInput endereco,
        String tipoCozinha,
        LocalTime horarioAbertura,
        LocalTime horarioFechamento
) {
}
