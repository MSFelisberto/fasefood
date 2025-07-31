package br.com.fiap.fasefood.application.usecases.autenticacao.atualizar;

import br.com.fiap.fasefood.infra.controllers.dto.ChangeUserPasswordDTO;

public interface AlterarSenhaUsuarioUseCase {
    void alterarSenha(Long id, String novaSenha);
}