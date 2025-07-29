package br.com.fiap.fasefood.infra.controller.mapper.restaurante;

import br.com.fiap.fasefood.core.domain.entities.Endereco;
import br.com.fiap.fasefood.core.domain.entities.Restaurante;
import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.infra.controller.dto.restaurante.CreateRestauranteDTO;
import br.com.fiap.fasefood.infra.controller.dto.restaurante.RestauranteResponseDTO;
import br.com.fiap.fasefood.infra.controller.dto.UsuarioResponseDTO;

public class RestauranteMapper {

    public static Restaurante toDomain(CreateRestauranteDTO dto, Usuario dono) {
        Endereco endereco = new Endereco(
                null,
                dto.endereco().logradouro(),
                dto.endereco().numero(),
                dto.endereco().cep(),
                dto.endereco().complemento(),
                dto.endereco().bairro(),
                dto.endereco().cidade(),
                dto.endereco().uf()
        );

        return new Restaurante(
                null,
                dto.nome(),
                endereco,
                dto.tipoCozinha(),
                dto.horarioAbertura(),
                dto.horarioFechamento(),
                dono,
                true
        );
    }

    public static RestauranteResponseDTO toResponseDTO(Restaurante restaurante) {
        return new RestauranteResponseDTO(
                restaurante.getId(),
                restaurante.getNome(),
                restaurante.getEndereco(),
                restaurante.getTipoCozinha(),
                restaurante.getHorarioAbertura(),
                restaurante.getHorarioFechamento(),
                new UsuarioResponseDTO(
                        restaurante.getDono().getId(),
                        restaurante.getDono().getNome(),
                        restaurante.getDono().getEmail(),
                        restaurante.getDono().getLogin()
                )
        );
    }
}