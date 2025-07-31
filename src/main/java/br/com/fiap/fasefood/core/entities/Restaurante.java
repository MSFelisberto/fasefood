package br.com.fiap.fasefood.core.entities;

import java.time.LocalTime;

public class Restaurante {

    private Long id;
    private String nome;
    private Endereco endereco;
    private String tipoCozinha;
    private LocalTime horarioAbertura;
    private LocalTime horarioFechamento;
    private Usuario dono;
    private boolean ativo;

    public Restaurante(Long id, String nome, Endereco endereco, String tipoCozinha, LocalTime horarioAbertura, LocalTime horarioFechamento, Usuario dono, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.tipoCozinha = tipoCozinha;
        this.horarioAbertura = horarioAbertura;
        this.horarioFechamento = horarioFechamento;
        this.dono = dono;
        this.ativo = ativo;
    }

    public void atualizarInformacoes(String nome, String tipoCozinha, LocalTime horarioAbertura, LocalTime horarioFechamento) {
        if (nome != null) this.nome = nome;
        if (tipoCozinha != null) this.tipoCozinha = tipoCozinha;
        if (horarioAbertura != null) this.horarioAbertura = horarioAbertura;
        if (horarioFechamento != null) this.horarioFechamento = horarioFechamento;
    }

    public void desativar() {
        this.ativo = false;
    }

    // Getters
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public Endereco getEndereco() { return endereco; }
    public String getTipoCozinha() { return tipoCozinha; }
    public LocalTime getHorarioAbertura() { return horarioAbertura; }
    public LocalTime getHorarioFechamento() { return horarioFechamento; }
    public Usuario getDono() { return dono; }
    public boolean isAtivo() { return ativo; }
}