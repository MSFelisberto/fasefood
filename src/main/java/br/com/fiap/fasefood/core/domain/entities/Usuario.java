package br.com.fiap.fasefood.core.domain.entities;


import java.time.LocalDate;

public class Usuario {

    private Long id;
    private String nome;
    private String email;
    private String login;
    private String senha;
    private LocalDate dataUltimaAtualizacao;
    private Endereco endereco;
    private TipoUsuario tipoUsuario;
    private boolean ativo;

    public Usuario(Long id, String nome, String email, String login, String senha,
                   LocalDate dataUltimaAtualizacao, Endereco endereco,
                   TipoUsuario tipoUsuario, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
        this.endereco = endereco;
        this.tipoUsuario = tipoUsuario;
        this.ativo = ativo;
    }

    public void atualizarInformacoes(String nome, String email) {
        if (nome != null) this.nome = nome;
        if (email != null) this.email = email;
        this.dataUltimaAtualizacao = LocalDate.now();
    }

    public void alterarSenha(String novaSenha) {
        if (novaSenha != null && !novaSenha.isBlank()) {
            this.senha = novaSenha;
            this.dataUltimaAtualizacao = LocalDate.now();
        }
    }

    public void alterarTipoUsuario(TipoUsuario novoTipoUsuario) {
        if (novoTipoUsuario != null) {
            this.tipoUsuario = novoTipoUsuario;
            this.dataUltimaAtualizacao = LocalDate.now();
        }
    }

    public void desativar() {
        this.ativo = false;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getLogin() { return login; }
    public String getSenha() { return senha; }
    public LocalDate getDataUltimaAtualizacao() { return dataUltimaAtualizacao; }
    public Endereco getEndereco() { return endereco; }
    public TipoUsuario getTipoUsuario() { return tipoUsuario; }
    public boolean isAtivo() { return ativo; }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}