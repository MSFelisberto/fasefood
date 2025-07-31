package br.com.fiap.fasefood.application.usecases.usuario.criar;

import br.com.fiap.fasefood.core.entities.Usuario;

public interface CriarUsuarioUseCase {
    CriarUsuarioOutput criarUsuario(CriarUsuarioInput criarUsuarioInput);
}