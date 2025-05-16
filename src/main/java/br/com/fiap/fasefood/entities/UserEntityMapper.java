package br.com.fiap.fasefood.entities;

import br.com.fiap.fasefood.core.domain.User;

public class UserEntityMapper {
    public static User toDomain(UserEntity userEntity){
        return new User(
                userEntity.getId(),
                userEntity.getNome(),
                userEntity.getEmail(),
                userEntity.getLogin(),
                userEntity.getSenha(),
                userEntity.getDataUltimaAtualizacao(),
                EnderecoEntityMapper.toDto(userEntity.getEndereco()),
                userEntity.getTipoUsuario(),
                true
        );
    };

    public static UserEntity toEntity(User user){
        return new UserEntity(
                user.getId(),
                user.getNome(),
                user.getEmail(),
                user.getLogin(),
                user.getSenha(),
                user.getDataUltimaAtualizacao(),
                user.getTipoUsuario(),
                EnderecoEntityMapper.toEntity(user.getEndereco())
        );
    }
}
