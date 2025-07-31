package br.com.fiap.fasefood.application.usecases.restaurante.listar;

import br.com.fiap.fasefood.infra.controllers.dto.restaurante.RestauranteResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListarRestaurantesUseCase {
    Page<RestauranteResponseDTO> listar(Pageable pageable);
}