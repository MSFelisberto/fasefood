package br.com.fiap.fasefood.infra.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "restaurantes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class RestauranteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private EnderecoEntity endereco;

    private String tipoCozinha;

    private LocalTime horarioAbertura;

    private LocalTime horarioFechamento;

    @ManyToOne
    @JoinColumn(name = "dono_id", nullable = false)
    private UsuarioEntity dono;

    private boolean ativo;
}