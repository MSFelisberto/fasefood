package br.com.fiap.fasefood.dtos;

import br.com.fiap.fasefood.entities.UserEntity;
import br.com.fiap.fasefood.enums.ETipoUsuario;

public record ListUserDTO(

        Long id,
        String nome,
        String email,
        String login,
        ETipoUsuario tipoUsuario) {

    public ListUserDTO(UserEntity userEntity){
        this(userEntity.getId(), userEntity.getNome(), userEntity.getEmail(), userEntity.getLogin(), userEntity.getTipoUsuario());
    }
}
