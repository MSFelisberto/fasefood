package br.com.fiap.fasefood.infra.controller.dto;

import br.com.fiap.fasefood.core.domain.entities.Cardapio;

import java.time.LocalDate;

public record ListCardapioDTO(
    Long id,
    String nome,
    String descricao,
    LocalDate dataCriacao,
    LocalDate dataUltimoUpdate) {

    public ListCardapioDTO(Cardapio cardapio){
        this(cardapio.getId(),cardapio.getNome(), cardapio.getDescricao(),cardapio.getDataCriacao(), cardapio.getDataUltimoUpdate());
    }

}
