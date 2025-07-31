package br.com.fiap.fasefood.infra.controllers.mapper.restaurante;

import br.com.fiap.fasefood.core.entities.Endereco;
import br.com.fiap.fasefood.core.entities.Restaurante;
import br.com.fiap.fasefood.infra.controllers.mapper.EnderecoEntityMapper;
import br.com.fiap.fasefood.infra.controllers.mapper.UserEntityMapper;
import br.com.fiap.fasefood.infra.persistence.entities.RestauranteEntity;

public class RestauranteEntityMapper {

    public static Restaurante toDomain(RestauranteEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Restaurante(
                entity.getId(),
                entity.getNome(),
                Endereco.criarEndereco(
                        entity.getEndereco().getId(),
                        entity.getEndereco().getLogradouro(),
                        entity.getEndereco().getNumero(),
                        entity.getEndereco().getCep(),
                        entity.getEndereco().getComplemento(),
                        entity.getEndereco().getBairro(),
                        entity.getEndereco().getCidade(),
                        entity.getEndereco().getUf()

                ),
                entity.getTipoCozinha(),
                entity.getHorarioAbertura(),
                entity.getHorarioFechamento(),
                UserEntityMapper.toDomain(entity.getDono()),
                entity.isAtivo()
        );
    }

    public static RestauranteEntity toEntity(Restaurante domain) {
        if (domain == null) {
            return null;
        }
        return new RestauranteEntity(
                domain.getId(),
                domain.getNome(),
                EnderecoEntityMapper.toEntity(domain.getEndereco()),
                domain.getTipoCozinha(),
                domain.getHorarioAbertura(),
                domain.getHorarioFechamento(),
                UserEntityMapper.toEntity(domain.getDono()),
                domain.isAtivo()
        );
    }
}