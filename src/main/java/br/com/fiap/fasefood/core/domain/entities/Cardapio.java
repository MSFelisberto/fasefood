package br.com.fiap.fasefood.core.domain.entities;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class Cardapio {

    private Long id;
    private String nome;
    private String descricao;
    private LocalDate dataCriacao;
    private LocalDate dataUltimoUpdate;
    private List<ItemCardapio> itensCardapio;

    /**
     *  Falta o restaurante
     */


    public Cardapio() {
    }

    public Cardapio(String nome, String descricao, LocalDate dataCriacao, LocalDate dataUltimoUpdate,List<ItemCardapio> itensCardapio) {
        this.nome = nome;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
        this.dataUltimoUpdate = dataUltimoUpdate;
        this.itensCardapio = itensCardapio;
    }

    public Cardapio(Long id, String nome, String descricao, LocalDate dataCriacao, LocalDate dataUltimoUpdate,List<ItemCardapio> itensCardapio) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
        this.dataUltimoUpdate = dataUltimoUpdate;
        this.itensCardapio = itensCardapio;
    }

    public void atualizar(String nome, String descricao) {
        if (nome != null) this.nome = nome;
        if (descricao != null) this.descricao = descricao;
        this.dataUltimoUpdate = LocalDate.now();
    }
}

