package br.com.fiap.fasefood.infra.presenters.restaurante;

import br.com.fiap.fasefood.core.domain.entities.restaurante.Restaurante;
import br.com.fiap.fasefood.infra.controller.dto.EnderecoDTO;
import br.com.fiap.fasefood.infra.controller.dto.restaurante.RestauranteResponseDTO;

public class RestaurantePresenter {
    public static RestauranteResponseDTO toDTO(Restaurante restaurante) {
        return new RestauranteResponseDTO(
                restaurante.getNome(),
                new EnderecoDTO(
                        restaurante.getEndereco().getLogradouro(),
                        restaurante.getEndereco().getNumero(),
                        restaurante.getEndereco().getCep(),
                        restaurante.getEndereco().getComplemento(),
                        restaurante.getEndereco().getBairro(),
                        restaurante.getEndereco().getCidade(),
                        restaurante.getEndereco().getUf()
                ),
                restaurante.getTipoCozinha(),
                restaurante.getHorarioFuncionamento(),
                restaurante.getUsuario().getEmail()
        );
    }
}
