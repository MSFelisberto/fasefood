package br.com.fiap.fasefood.application.usecase.autenticacao.atualizar;

import br.com.fiap.fasefood.infra.controller.dto.ChangeUserPasswordDTO;

public interface AlterarSenhaUsuarioUseCase {
    void alterarSenha(Long id, ChangeUserPasswordDTO novaSenha);
}