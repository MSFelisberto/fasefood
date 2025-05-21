package br.com.fiap.fasefood.entities;

import br.com.fiap.fasefood.dtos.CreateUserDTO;
import br.com.fiap.fasefood.enums.ETipoUsuario;
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
public class UserEntity {
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
    private Endereco endereco;

    private boolean ativo;
}
