package br.com.fiap.fasefood.infra.controller.dto;

import br.com.fiap.fasefood.core.domain.entities.Endereco;
import br.com.fiap.fasefood.core.domain.entities.TipoUsuario;

public record ListUserDTO(

        Long id,
        String nome,
        String email,
        String login,
        TipoUsuario tipoUsuario,
        Endereco endereco) {

}