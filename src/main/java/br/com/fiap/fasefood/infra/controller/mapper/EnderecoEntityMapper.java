package br.com.fiap.fasefood.infra.controller.mapper;

import br.com.fiap.fasefood.core.domain.entities.Endereco;
import br.com.fiap.fasefood.infra.persistence.entities.EnderecoEntity;

public class EnderecoEntityMapper {

    public static Endereco toDomain(EnderecoEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Endereco(
            entity.getId(),
            entity.getLogradouro(),
            entity.getNumero(),
            entity.getCep(),
            entity.getComplemento(),
            entity.getBairro(),
            entity.getCidade(),
            entity.getUf()
        );
    }

    public static EnderecoEntity toEntity(Endereco endereco) {
        if (endereco == null) {
            return null;
        }
        return new EnderecoEntity(
            endereco.getId(),
            endereco.getLogradouro(),
            endereco.getNumero(),
            endereco.getCep(),
            endereco.getComplemento(),
            endereco.getBairro(),
            endereco.getCidade(),
            endereco.getUf()
        );
    }
}