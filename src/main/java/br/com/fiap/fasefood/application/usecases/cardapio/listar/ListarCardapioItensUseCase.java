package br.com.fiap.fasefood.application.usecases.cardapio.listar;

import br.com.fiap.fasefood.infra.controllers.dto.cardapio.CardapioItemResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListarCardapioItensUseCase {
    Page<CardapioItemResponseDTO> listar(Long restauranteId, Pageable pageable);
}