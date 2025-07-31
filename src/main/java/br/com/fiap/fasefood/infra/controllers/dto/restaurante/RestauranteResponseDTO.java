package br.com.fiap.fasefood.infra.controllers.dto.restaurante;

import br.com.fiap.fasefood.core.entities.Endereco;
import br.com.fiap.fasefood.infra.controllers.dto.UsuarioResponseDTO;

import java.time.LocalTime;

public record RestauranteResponseDTO(
        Long id,
        String nome,
        Endereco endereco,
        String tipoCozinha,
        LocalTime horarioAbertura,
        LocalTime horarioFechamento,
        UsuarioResponseDTO dono
) {}