package br.com.fiap.fasefood.core.usecase.interfaces;

import br.com.fiap.fasefood.infra.controller.dto.ChangeUserPasswordDTO;

public interface AlterarSenhaUsuarioUseCase {
    void alterarSenha(Long id, ChangeUserPasswordDTO novaSenha);
}