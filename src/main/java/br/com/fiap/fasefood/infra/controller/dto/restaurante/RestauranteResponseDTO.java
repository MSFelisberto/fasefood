package br.com.fiap.fasefood.infra.controller.dto.restaurante;

import br.com.fiap.fasefood.core.domain.entities.Endereco;
import br.com.fiap.fasefood.infra.controller.dto.UsuarioResponseDTO;

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