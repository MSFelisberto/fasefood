package br.com.fiap.fasefood.core.usecase.interfaces.restaurante;

import br.com.fiap.fasefood.infra.controller.dto.restaurante.RestauranteResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListarRestaurantesUseCase {
    Page<RestauranteResponseDTO> listar(Pageable pageable);
}