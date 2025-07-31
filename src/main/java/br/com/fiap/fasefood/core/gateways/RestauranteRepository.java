package br.com.fiap.fasefood.core.gateways;

import br.com.fiap.fasefood.core.entities.Restaurante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RestauranteRepository {
    Restaurante salvar(Restaurante restaurante);
    Optional<Restaurante> findById(Long id);
    Page<Restaurante> listarTodosAtivos(Pageable paginacao);

}