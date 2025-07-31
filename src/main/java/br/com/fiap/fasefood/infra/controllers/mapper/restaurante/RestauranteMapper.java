package br.com.fiap.fasefood.infra.controllers.mapper.restaurante;

import br.com.fiap.fasefood.core.entities.Endereco;
import br.com.fiap.fasefood.core.entities.Restaurante;
import br.com.fiap.fasefood.core.entities.Usuario;
import br.com.fiap.fasefood.infra.controllers.dto.restaurante.CreateRestauranteDTO;
import br.com.fiap.fasefood.infra.controllers.dto.restaurante.RestauranteResponseDTO;
import br.com.fiap.fasefood.infra.controllers.dto.UsuarioResponseDTO;

public class RestauranteMapper {

    public static Restaurante toDomain(CreateRestauranteDTO dto, Usuario dono) {
        return new Restaurante(
                null,
                dto.nome(),
                Endereco.criarEndereco(
                        null,
                        dto.endereco().logradouro(),
                        dto.endereco().numero(),
                        dto.endereco().cep(),
                        dto.endereco().complemento(),
                        dto.endereco().bairro(),
                        dto.endereco().cidade(),
                        dto.endereco().uf()
                ),
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