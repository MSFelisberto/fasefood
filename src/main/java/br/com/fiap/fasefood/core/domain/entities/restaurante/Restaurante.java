package br.com.fiap.fasefood.core.domain.entities.restaurante;

import br.com.fiap.fasefood.core.domain.entities.Endereco;
import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.core.domain.enums.TipoCozinha;
import br.com.fiap.fasefood.core.exceptions.restaurante.EnderecoNaoPodeSerNuloException;
import br.com.fiap.fasefood.core.exceptions.restaurante.TipoCozinhaNaoPodeSerNuloException;

import java.time.LocalDateTime;

public class Restaurante {
    private Long id;
    private String nome;
    private Endereco endereco;
    private TipoCozinha tipoCozinha;
    private LocalDateTime horarioFuncionamento;
    private Usuario usuario;

    private Restaurante(String nome, Endereco endereco, TipoCozinha tipoCozinha, LocalDateTime horarioFuncionamento, Usuario usuario) {
        this.nome = nome;
        this.endereco = endereco;
        this.tipoCozinha = tipoCozinha;
        this.horarioFuncionamento = horarioFuncionamento;
        this.usuario = usuario;
    }
    private Restaurante(Long id, String nome, Endereco endereco, TipoCozinha tipoCozinha, LocalDateTime horarioFuncionamento, Usuario usuario) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.tipoCozinha = tipoCozinha;
        this.horarioFuncionamento = horarioFuncionamento;
        this.usuario = usuario;
    }

    public static Restaurante createRestaurante(String nome, Endereco endereco, TipoCozinha tipoCozinha, LocalDateTime horarioFuncionamento, Usuario usuario) {
        Restaurante.validaAddress(endereco);
        Restaurante.validaTipoCozinha(tipoCozinha);
        return new Restaurante(
                nome,
                endereco,
                tipoCozinha,
                horarioFuncionamento,
                usuario
        );
    }

    public static Restaurante createRestaurante(Long id, String nome, Endereco endereco, TipoCozinha tipoCozinha, LocalDateTime horarioFuncionamento, Usuario usuario) {
        Restaurante.validaAddress(endereco);
        Restaurante.validaTipoCozinha(tipoCozinha);
        return new Restaurante(
                id,
                nome,
                endereco,
                tipoCozinha,
                horarioFuncionamento,
                usuario
        );
    }

    private static void validaAddress(Endereco endereco) {
        if (endereco == null) {
            throw new EnderecoNaoPodeSerNuloException("Endereço não pode ser nulo");
        }
    }

    private static void validaTipoCozinha(TipoCozinha tipoCozinha) {
        if (tipoCozinha == null) {
            throw new TipoCozinhaNaoPodeSerNuloException("Tipo de cozinha não pode ser nulo");
        }
    }


    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public TipoCozinha getTipoCozinha() {
        return tipoCozinha;
    }

    public LocalDateTime getHorarioFuncionamento() {
        return horarioFuncionamento;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
