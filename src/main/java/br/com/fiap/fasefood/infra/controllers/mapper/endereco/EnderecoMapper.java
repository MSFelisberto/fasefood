package br.com.fiap.fasefood.infra.controllers.mapper.endereco;

import br.com.fiap.fasefood.application.usecases.shared.endereco.EnderecoInput;
import br.com.fiap.fasefood.infra.controllers.dto.EnderecoDTO;

public class EnderecoMapper {
    public static EnderecoInput toEnderecoInput(EnderecoDTO dto) {
        return new EnderecoInput(
                dto.logradouro(),
                dto.numero(),
                dto.cep(),
                dto.complemento(),
                dto.bairro(),
                dto.cidade(),
                dto.uf()
        );

    }
}
