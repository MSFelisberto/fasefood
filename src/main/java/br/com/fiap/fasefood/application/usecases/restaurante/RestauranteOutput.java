package br.com.fiap.fasefood.application.usecases.restaurante;

import br.com.fiap.fasefood.application.usecases.usuario.UsuarioOutput;
import br.com.fiap.fasefood.core.entities.Endereco;

import java.time.LocalTime;

public record RestauranteOutput (
        Long id,
        String nome,
        Endereco endereco,
        String tipoCozinha,
        LocalTime horarioAbertura,
        LocalTime horarioFechamento,
        UsuarioOutput dono
) {
}
