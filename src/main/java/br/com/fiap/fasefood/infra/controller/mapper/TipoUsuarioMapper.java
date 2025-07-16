package br.com.fiap.fasefood.infra.controller.mapper;

import br.com.fiap.fasefood.core.domain.entities.TipoUsuario;
import br.com.fiap.fasefood.infra.persistence.entities.TipoUsuarioEntity;

public class TipoUsuarioMapper {

    public static TipoUsuario toDomain(TipoUsuarioEntity entity) {
        if (entity == null) {
            return null;
        }

        return new TipoUsuario(
                entity.getId(),
                entity.getNome()
        );
    }

    public static TipoUsuarioEntity toEntity(TipoUsuario domain) {
        if (domain == null) {
            return null;
        }

        return new TipoUsuarioEntity(
                domain.getId(),
                domain.getNome()
        );
    }
}
