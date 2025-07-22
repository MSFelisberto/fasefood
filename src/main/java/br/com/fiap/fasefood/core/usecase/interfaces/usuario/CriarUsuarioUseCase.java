package br.com.fiap.fasefood.core.usecase.interfaces.usuario;

import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.infra.controller.dto.CreateUserDTO;

public interface CriarUsuarioUseCase {
    Usuario criarUsuario(CreateUserDTO createUserDTO);
}