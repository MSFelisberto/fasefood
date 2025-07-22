package br.com.fiap.fasefood.core.usecase.gateways;

import br.com.fiap.fasefood.core.domain.entities.restaurante.Restaurante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RestauranteRepository {
    Restaurante create(Restaurante restaurante);
    Page<Restaurante> findAll(Pageable pageable);
    Optional<Restaurante> findById(Long id);
    void deleteById(Long id);
    Restaurante updateById(Long id, Restaurante restaurante);
}
