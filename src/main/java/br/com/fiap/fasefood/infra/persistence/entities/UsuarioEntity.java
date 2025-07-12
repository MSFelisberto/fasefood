package br.com.fiap.fasefood.infra.persistence.entities;

import br.com.fiap.fasefood.core.domain.enums.ETipoUsuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "usuario")
@Table(name = "usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String login;
    private String senha;
    private LocalDate dataUltimaAtualizacao;

    @Enumerated(EnumType.STRING)
    private ETipoUsuario tipoUsuario;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "endereco_id")
    private EnderecoEntity enderecoEntity;

    private boolean ativo;
}
