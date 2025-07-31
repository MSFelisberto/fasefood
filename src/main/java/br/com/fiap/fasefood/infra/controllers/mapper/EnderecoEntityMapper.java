package br.com.fiap.fasefood.infra.controllers.mapper;

import br.com.fiap.fasefood.core.entities.Endereco;
import br.com.fiap.fasefood.infra.persistence.entities.EnderecoEntity;

public class EnderecoEntityMapper {

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