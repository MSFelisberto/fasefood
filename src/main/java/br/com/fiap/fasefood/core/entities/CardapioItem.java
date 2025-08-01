package br.com.fiap.fasefood.core.entities;

import java.math.BigDecimal;

public class CardapioItem {

    private Long id;
    private Cardapio cardapio;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private boolean apenasNoLocal;
    private String caminhoFoto;
    private boolean ativo;

    private CardapioItem(Long id, Cardapio cardapio, String nome, String descricao, BigDecimal preco, boolean apenasNoLocal, String caminhoFoto, boolean ativo) {
        this.id = id;
        this.cardapio = cardapio;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.apenasNoLocal = apenasNoLocal;
        this.caminhoFoto = caminhoFoto;
        this.ativo = ativo;
    }

    public static CardapioItem create(Long id, Cardapio cardapio, String nome, String descricao, BigDecimal preco, boolean apenasNoLocal, String caminhoFoto, boolean ativo) {
        return new CardapioItem(
                id,
                cardapio,
                nome,
                descricao,
                preco,
                apenasNoLocal,
                caminhoFoto,
                ativo
        );
    }

    public void atualizar(String nome, String descricao, BigDecimal preco, Boolean apenasNoLocal, String caminhoFoto) {
        if (nome != null) this.nome = nome;
        if (descricao != null) this.descricao = descricao;
        if (preco != null) this.preco = preco;
        if (apenasNoLocal != null) this.apenasNoLocal = apenasNoLocal;
        if (caminhoFoto != null) this.caminhoFoto = caminhoFoto;
    }

    public void desativar() {
        this.ativo = false;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public BigDecimal getPreco() { return preco; }
    public boolean isApenasNoLocal() { return apenasNoLocal; }
    public String getCaminhoFoto() { return caminhoFoto; }
    public boolean isAtivo() { return ativo; }
    public Cardapio getCardapio() { return cardapio; }
}