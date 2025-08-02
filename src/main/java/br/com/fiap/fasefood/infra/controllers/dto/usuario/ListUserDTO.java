package br.com.fiap.fasefood.infra.controllers.dto.usuario;

import br.com.fiap.fasefood.application.usecases.shared.endereco.EnderecoInput;
import br.com.fiap.fasefood.core.entities.TipoUsuario;

public record ListUserDTO(

        Long id,
        String nome,
        String email,
        String login,
        TipoUsuario tipoUsuario,
        EnderecoInput endereco
) {

}