package br.com.fiap.fasefood.infra.controllers.dto;


public record LoginResponseDTO(

        boolean sucesso,

        String mensagem
) {
}
