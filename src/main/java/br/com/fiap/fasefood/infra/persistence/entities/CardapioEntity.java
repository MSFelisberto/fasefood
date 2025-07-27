package br.com.fiap.fasefood.infra.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cardapios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class CardapioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurante_id", nullable = false)
    private RestauranteEntity restaurante;

    private String nome;
    private String descricao;
    private boolean ativo;
}