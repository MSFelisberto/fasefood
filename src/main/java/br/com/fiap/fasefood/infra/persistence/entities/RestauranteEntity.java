package br.com.fiap.fasefood.infra.persistence.entities;

import br.com.fiap.fasefood.core.domain.entities.Endereco;
import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.core.domain.enums.TipoCozinha;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "restaurantes")
@Getter
@Setter
public class RestauranteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private EnderecoEntity endereco;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cozinha", nullable = false)
    private TipoCozinha tipoCozinha;

    @Column(name = "horario_funcionamento")
    private LocalDateTime horarioFuncionamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private UsuarioEntity usuario;

    public RestauranteEntity() {}

    public RestauranteEntity(
            String nome,
            EnderecoEntity endereco,
            TipoCozinha tipoCozinha,
            LocalDateTime horarioFuncionamento,
            UsuarioEntity usuario
    ) {
        this.nome = nome;
        this.endereco = endereco;
        this.tipoCozinha = tipoCozinha;
        this.horarioFuncionamento = horarioFuncionamento;
        this.usuario = usuario;
    }
}
