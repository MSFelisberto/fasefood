package br.com.fiap.fasefood.dtos;

import br.com.fiap.fasefood.entities.User;
import br.com.fiap.fasefood.enums.ETipoUsuario;

public record ListUserDTO(Long id, String nome, String email, String login, ETipoUsuario tipoUsuario) {

    public ListUserDTO(User user){
        this(user.getId(), user.getNome(), user.getEmail(), user.getLogin(), user.getTipoUsuario());
    }
}
