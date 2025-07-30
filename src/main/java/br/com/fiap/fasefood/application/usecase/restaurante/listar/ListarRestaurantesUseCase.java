package br.com.fiap.fasefood.application.usecase.restaurante.listar;

import br.com.fiap.fasefood.infra.controller.dto.restaurante.RestauranteResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListarRestaurantesUseCase {
    Page<RestauranteResponseDTO> listar(Pageable pageable);
}