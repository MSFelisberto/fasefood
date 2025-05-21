package br.com.fiap.fasefood.entities;

import br.com.fiap.fasefood.dtos.EnderecoDTO;

public class EnderecoEntityMapper {
    public static EnderecoDTO toDto(Endereco endereco) {
        return new EnderecoDTO(
            endereco.getLogradouro(),
            endereco.getNumero(),
            endereco.getCep(),
            endereco.getComplemento(),
            endereco.getBairro(),
            endereco.getCidade(),
            endereco.getUf()
        );
    }

    public static Endereco toEntity(EnderecoDTO enderecoDTO) {
        return new Endereco(enderecoDTO);
    }
}
