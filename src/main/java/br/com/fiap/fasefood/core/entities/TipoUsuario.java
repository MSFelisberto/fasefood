package br.com.fiap.fasefood.core.entities;

public class TipoUsuario {

    private Long id;
    private String nome;

    public TipoUsuario() {}

    public TipoUsuario(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
