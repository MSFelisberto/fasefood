package br.com.fiap.fasefood.application.usecases.restaurante.criar;

import br.com.fiap.fasefood.application.usecases.shared.endereco.EnderecoInput;

import java.time.LocalTime;

public record CriarRestauranteInput(
        String nome,
        EnderecoInput endereco,
        String tipoCozinha,
        LocalTime horarioAbertura,
        LocalTime horarioFechamento,
        Long donoId
) {
}
