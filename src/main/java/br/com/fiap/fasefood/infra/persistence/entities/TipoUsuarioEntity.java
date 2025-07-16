package br.com.fiap.fasefood.infra.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tipo_usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class TipoUsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;
}
