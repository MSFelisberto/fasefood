package br.com.fiap.fasefood.core.domain.entities;

import lombok.Getter;

@Getter
public class ItemCardapio {

    private Long id;
    private String nome;
    private String descricao;
    private double preco;
    private Boolean flagDisponibilidade;
    private String fotoPath;

    public ItemCardapio() {
    }
    public ItemCardapio(String nome, String descricao, double preco, Boolean flagDisponibilidade, String fotoPath) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.flagDisponibilidade = flagDisponibilidade;
        this.fotoPath = fotoPath;
    }
    public ItemCardapio(Long id, String nome, String descricao, double preco, Boolean flagDisponibilidade, String fotoPath) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.flagDisponibilidade = flagDisponibilidade;
        this.fotoPath = fotoPath;
    }

}

