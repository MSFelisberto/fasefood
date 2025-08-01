package br.com.fiap.fasefood.core.usecase.interfaces.cardapio;

import br.com.fiap.fasefood.infra.controller.dto.cardapio.CardapioResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListarCardapiosUseCase {
    Page<CardapioResponseDTO> listar(Long restauranteId, Pageable pageable);
}