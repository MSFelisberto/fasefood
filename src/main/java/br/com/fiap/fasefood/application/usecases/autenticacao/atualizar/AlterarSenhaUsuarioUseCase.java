package br.com.fiap.fasefood.application.usecases.autenticacao.atualizar;

public interface AlterarSenhaUsuarioUseCase {
    void alterarSenha(Long id, String novaSenha);
}