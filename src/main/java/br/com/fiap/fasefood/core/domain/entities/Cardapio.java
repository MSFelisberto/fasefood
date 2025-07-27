package br.com.fiap.fasefood.core.domain.entities;

public class Cardapio {

    private Long id;
    private Restaurante restaurante;
    private String nome;
    private String descricao;
    private boolean ativo;

    public Cardapio(Long id, Restaurante restaurante, String nome, String descricao, boolean ativo) {
        this.id = id;
        this.restaurante = restaurante;
        this.nome = nome;
        this.descricao = descricao;
        this.ativo = ativo;
    }

    public void atualizar(String nome, String descricao) {
        if (nome != null) this.nome = nome;
        if (descricao != null) this.descricao = descricao;
    }

    public void desativar() {
        this.ativo = false;
    }

    // Getters
    public Long getId() { return id; }
    public Restaurante getRestaurante() { return restaurante; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public boolean isAtivo() { return ativo; }
}