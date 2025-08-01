package br.com.fiap.fasefood.application.usecases.cardapio.listar;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListarCardapiosUseCase {
    Page<CardapioResponseOutput> listar(Long restauranteId, Pageable pageable);
}