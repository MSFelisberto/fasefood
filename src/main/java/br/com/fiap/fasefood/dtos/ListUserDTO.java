package br.com.fiap.fasefood.dtos;

import br.com.fiap.fasefood.core.domain.User;
import br.com.fiap.fasefood.entities.Endereco;
import br.com.fiap.fasefood.entities.UserEntity;
import br.com.fiap.fasefood.enums.ETipoUsuario;

public record ListUserDTO(

        Long id,
        String nome,
        String email,
        String login,
        ETipoUsuario tipoUsuario,
        Endereco endereco) {

    public ListUserDTO(User user){
        this(user.getId(), user.getNome(), user.getEmail(), user.getLogin(), user.getTipoUsuario(), user.getEndereco());
    }
}
