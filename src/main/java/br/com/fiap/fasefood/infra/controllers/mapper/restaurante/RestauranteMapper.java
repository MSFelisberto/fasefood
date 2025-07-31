package br.com.fiap.fasefood.infra.controllers.mapper.restaurante;

import br.com.fiap.fasefood.application.usecases.restaurante.RestauranteOutput;
import br.com.fiap.fasefood.application.usecases.restaurante.criar.CriarRestauranteInput;
import br.com.fiap.fasefood.core.entities.Endereco;
import br.com.fiap.fasefood.core.entities.Restaurante;
import br.com.fiap.fasefood.core.entities.Usuario;
import br.com.fiap.fasefood.infra.controllers.dto.restaurante.CreateRestauranteDTO;
import br.com.fiap.fasefood.infra.controllers.dto.restaurante.RestauranteResponseDTO;
import br.com.fiap.fasefood.infra.controllers.dto.UsuarioResponseDTO;
import br.com.fiap.fasefood.infra.controllers.mapper.endereco.EnderecoMapper;

public class RestauranteMapper {
    public static Restaurante toDomain(CreateRestauranteDTO dto, Usuario dono) {
        return Restaurante.create(
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

    public static RestauranteResponseDTO toResponseDTO(RestauranteOutput restaurante) {
        return new RestauranteResponseDTO(
                restaurante.id(),
                restaurante.nome(),
                restaurante.endereco(),
                restaurante.tipoCozinha(),
                restaurante.horarioAbertura(),
                restaurante.horarioFechamento(),
                new UsuarioResponseDTO(
                        restaurante.dono().id(),
                        restaurante.dono().nome(),
                        restaurante.dono().email(),
                        restaurante.dono().login()
                )
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

    public static CriarRestauranteInput toCriarRestauranteInput(CreateRestauranteDTO restauranteDto) {
        return new CriarRestauranteInput(
                restauranteDto.nome(),
                EnderecoMapper.toEnderecoInput(restauranteDto.endereco()),
                restauranteDto.tipoCozinha(),
                restauranteDto.horarioAbertura(),
                restauranteDto.horarioFechamento(),
                restauranteDto.donoId()
        );
    }
}