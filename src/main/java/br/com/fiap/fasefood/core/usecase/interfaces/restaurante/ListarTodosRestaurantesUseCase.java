package br.com.fiap.fasefood.core.usecase.interfaces.restaurante;

import br.com.fiap.fasefood.core.domain.entities.restaurante.Restaurante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListarTodosRestaurantesUseCase {
    Page<Restaurante> buscaTodosRestaurantes(Pageable pageable);
}
