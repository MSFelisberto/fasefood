package br.com.fiap.fasefood.infra.controller.dto;


public record LoginResponseDTO(

        boolean sucesso,

        String mensagem
) {
}
