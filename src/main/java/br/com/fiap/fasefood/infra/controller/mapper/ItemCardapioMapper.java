package br.com.fiap.fasefood.infra.controller.mapper;

import br.com.fiap.fasefood.core.domain.entities.Cardapio;
import br.com.fiap.fasefood.core.domain.entities.ItemCardapio;
import br.com.fiap.fasefood.core.domain.entities.TipoUsuario;
import br.com.fiap.fasefood.infra.controller.dto.CreateItemCardapioDTO;
import br.com.fiap.fasefood.infra.persistence.entities.CardapioEntity;
import br.com.fiap.fasefood.infra.persistence.entities.ItemCardapioEntity;
import br.com.fiap.fasefood.infra.persistence.entities.TipoUsuarioEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ItemCardapioMapper {

    public static List<ItemCardapioEntity> toEntity(List<ItemCardapio> domain) {
        if (domain == null) {
            return null;
        }

        return domain.stream()
                .map(dto -> {
                    ItemCardapioEntity item = new ItemCardapioEntity(dto.getId(), dto.getNome(), dto.getDescricao(),
                            dto.getPreco(), dto.getFlagDisponibilidade(), dto.getFotoPath());
                    return item;
                })
                .collect(Collectors.toList());
    }


    public static List<ItemCardapio> toDomain(List<ItemCardapioEntity> listEntity) {
        if (listEntity == null) {
            return null;
        }

        return listEntity.stream()
                .map(dto -> {
                    ItemCardapio item = new ItemCardapio(dto.getId(), dto.getNome(), dto.getDescricao(),
                            dto.getPreco(), dto.getFlagDisponibilidade(), dto.getFoto());
                    return item;
                })
                .collect(Collectors.toList());
    }

    public static List<ItemCardapio> dtoToDomain(List<CreateItemCardapioDTO> listDTO) {
        if (listDTO == null) {
            return null;
        }

        return listDTO.stream()
                .map(dto -> {
                    ItemCardapio item = new ItemCardapio(dto.nome(), dto.descricao(),
                            dto.preco(), dto.flagDisponibilidade(), dto.fotoPath());
                    return item;
                })
                .collect(Collectors.toList());
    }

}
