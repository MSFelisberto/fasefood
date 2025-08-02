package br.com.fiap.fasefood.infra.controllers.dto.autenticacao;


public record LoginResponseDTO(
        boolean sucesso,
        String mensagem
) {
}
