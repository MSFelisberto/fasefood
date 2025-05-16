package br.com.fiap.fasefood.core.domain;

import br.com.fiap.fasefood.dtos.ChangeUserPasswordDTO;
import br.com.fiap.fasefood.dtos.EnderecoDTO;
import br.com.fiap.fasefood.dtos.UpdateUserDataDTO;
import br.com.fiap.fasefood.entities.Endereco;
import br.com.fiap.fasefood.entities.EnderecoEntityMapper;
import br.com.fiap.fasefood.enums.ETipoUsuario;

import java.time.LocalDate;

public class User {

    private Long id;
    private String nome;
    private String email;
    private String login;
    private String senha;
    private LocalDate dataUltimaAtualizacao;
    private EnderecoDTO endereco;

    private ETipoUsuario tipoUsuario;
    private boolean ativo;

    public User(
            Long id,
            String nome,
            String email,
            String login,
            String senha,
            LocalDate dataUltimaAtualizacao,
            EnderecoDTO endereco,
            ETipoUsuario tipoUsuario,
            boolean ativo
    ) {
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

    public void atualizarInformacoes(UpdateUserDataDTO data) {
        atualizarNome(data.nome());
        atualizarEmail(data.email());
        atualizarEndereco(data.endereco());
        this.dataUltimaAtualizacao = LocalDate.now();
    }

    public void changeUserPassword(ChangeUserPasswordDTO userData) {
        changePassword(userData);
        this.dataUltimaAtualizacao = LocalDate.now();
    }

    public void deleteUser() {
        this.ativo = false;
    }


    private void atualizarNome(String nome) {
        if(nome != null) {
            this.nome = nome;
        }
    }

    private void atualizarEmail(String email) {
        if(email != null) {
            this.email = email;
        }
    }

    private void atualizarEndereco(EnderecoDTO endereco) {
        if (endereco != null && this.endereco != null) {
            EnderecoEntityMapper.toEntity(this.endereco).atualizarInformacoesEndereco(endereco);
        }
    }

    private void changePassword(ChangeUserPasswordDTO userData) {
        if(senha != null) {
            this.senha = userData.senha();
        }
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public LocalDate getDataUltimaAtualizacao() {
        return dataUltimaAtualizacao;
    }

    public EnderecoDTO getEndereco() {
        return this.endereco;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public ETipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }
}
