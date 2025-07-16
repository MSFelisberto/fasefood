package br.com.fiap.fasefood.infra.controller.dto;

import br.com.fiap.fasefood.core.domain.entities.Endereco;
import br.com.fiap.fasefood.core.domain.entities.TipoUsuario;
import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.core.domain.enums.ETipoUsuario;

public record ListUserDTO(

        Long id,
        String nome,
        String email,
        String login,
        TipoUsuario tipoUsuario,
        Endereco endereco) {

    public ListUserDTO(Usuario user){
        this(user.getId(), user.getNome(), user.getEmail(), user.getLogin(), user.getTipoUsuario(), user.getEndereco());
    }
}
