package br.com.fiap.fasefood.infra.controllers.mapper.usuario;

import br.com.fiap.fasefood.application.usecases.shared.endereco.EnderecoInput;
import br.com.fiap.fasefood.application.usecases.usuario.listar.ListUserOutput;
import br.com.fiap.fasefood.infra.controllers.dto.ListUserDTO;

public class ListUserMapper {
    public static ListUserDTO toDTO(ListUserOutput listUserOutput) {
        return new ListUserDTO(
                listUserOutput.id(),
                listUserOutput.nome(),
                listUserOutput.email(),
                listUserOutput.login(),
                listUserOutput.tipoUsuario(),
                new EnderecoInput(
                        listUserOutput.endereco().getLogradouro(),
                        listUserOutput.endereco().getNumero(),
                        listUserOutput.endereco().getCep(),
                        listUserOutput.endereco().getComplemento(),
                        listUserOutput.endereco().getBairro(),
                        listUserOutput.endereco().getCidade(),
                        listUserOutput.endereco().getUf()
                )
        );
    }
}
