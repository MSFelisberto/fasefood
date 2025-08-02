package br.com.fiap.fasefood.infra.controllers.mapper.cardapio;

import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioInput;
import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioOutput;
import br.com.fiap.fasefood.application.usecases.cardapio.listar.CardapioResponseOutput;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PageOutput;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PaginationInput;
import br.com.fiap.fasefood.core.entities.Cardapio;
import br.com.fiap.fasefood.core.entities.Restaurante;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CardapioResponseDTO;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CreateCardapioDTO;
import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CreateCardapioResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class CardapioMapper {
    public static Cardapio toDomain(CreateCardapioDTO dto, Restaurante restaurante) {
        return Cardapio.create(null, restaurante, dto.nome(), dto.descricao(), true);
    }

    public static CardapioResponseDTO toResponseDTO(Cardapio cardapio) {
        return new CardapioResponseDTO(cardapio.getId(), cardapio.getRestaurante().getId(), cardapio.getNome(), cardapio.getDescricao());
    }
    public static CardapioResponseDTO toResponseDTO(CardapioResponseOutput cardapioResponseOutput) {
        return new CardapioResponseDTO(
                cardapioResponseOutput.id(),
                cardapioResponseOutput.restauranteId(),
                cardapioResponseOutput.nome(),
                cardapioResponseOutput.descricao()
        );
    }

    public static CriarCardapioInput toCriarCardapioInput(CreateCardapioDTO dto) {
        return new CriarCardapioInput(
                dto.restauranteId(),
                dto.nome(),
                dto.descricao(),
                CardapioItemMapper.toCriarCardapioItemInput(dto.itens())
        );
    }

    public static Page<CardapioResponseDTO> toRestauranteDtoPaginacao(PageOutput<CardapioResponseOutput> cardapioResponseOutput) {
        List<CardapioResponseDTO> dtos = cardapioResponseOutput.content()
                .stream()
                .map(CardapioMapper::toResponseDTO)
                .toList();

        return new PageImpl<>(
                dtos,
                PageRequest.of(cardapioResponseOutput.currentPage(), cardapioResponseOutput.size()),
                cardapioResponseOutput.totalElements()
        );
    }

    public static CreateCardapioResponseDTO toResponseCriarCardapioDTO(CriarCardapioOutput cardapioOutput) {
        if (cardapioOutput == null) {
            return null;
        }
        return new CreateCardapioResponseDTO(
                cardapioOutput.id()
        );
    }

    public static PaginationInput toPaginationInput(Pageable pageable) {
        if (pageable == null) {
            return new PaginationInput(0, 10, "id", "ASC");
        }

        String sortField = "id";
        String sortDirection = "ASC";

        if (pageable.getSort().isSorted()) {
            var order = pageable.getSort().iterator().next();
            sortField = order.getProperty();
            sortDirection = order.getDirection().name();
        }

        return new PaginationInput(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                sortField,
                sortDirection
        );
    }
}