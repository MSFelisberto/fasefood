package br.com.fiap.fasefood.core.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class Restaurante {
    private Long id;
    private String nome;
    private Endereco endereco;
    private String tipoCozinha;
    private LocalDateTime horarioFuncionamento;
    private Usuario usuario;

    private Restaurante(String nome, Endereco endereco, String tipoCozinha, LocalDateTime horarioFuncionamento, Usuario usuario) {
        this.nome = nome;
        this.endereco = endereco;
        this.tipoCozinha = tipoCozinha;
        this.horarioFuncionamento = horarioFuncionamento;
        this.usuario = usuario;
    }

    public static Restaurante createRestaurante() {
        return new Restaurante();
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Restaurante that = (Restaurante) obj;

        // If both have IDs, compare by ID only
        if (this.id != null && that.id != null) {
            return Objects.equals(this.id, that.id);
        }

        // If no ID available, compare by business fields
        return Objects.equals(this.nome, that.nome) &&
                Objects.equals(this.endereco, that.endereco) &&
                Objects.equals(this.tipoCozinha, that.tipoCozinha) &&
                Objects.equals(this.usuario, that.usuario);
    }


    @Override
    public int hashCode() {
        // If ID is available, use it for hash code
        if (this.id != null) {
            return Objects.hash(this.id);
        }

        // If no ID, use business fields
        return Objects.hash(this.nome, this.endereco, this.tipoCozinha, this.usuario);
    }
}
