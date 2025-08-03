package br.com.fiap.fasefood.application.usecases.autenticacao.autenticar;


public interface AutenticarUsuarioUseCase {
    LoginResponseOutput autenticar(LoginRequestInput loginInput);
}