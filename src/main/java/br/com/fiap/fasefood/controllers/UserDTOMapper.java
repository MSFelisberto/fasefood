package br.com.fiap.fasefood.controllers;

import br.com.fiap.fasefood.core.domain.User;
import br.com.fiap.fasefood.dtos.CreateUserDTO;
import br.com.fiap.fasefood.dtos.UpdateUserDataDTO;

import java.time.LocalDate;

public class UserDTOMapper {
    public static User toDomain(CreateUserDTO user){
        return new User(
                user.nome(),
                user.email(),
                user.login(),
                user.senha(),
                LocalDate.now(),
                user.endereco(),
                user.tipoUsuario(),
                true
        );
    }

//    static CreateUserDTO toDTO(User) {
//        return new CreateUserDTO();
//    }
}
