package br.com.fiap.fasefood.core.usecase.gateways;

import br.com.fiap.fasefood.core.domain.entities.Cardapio;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CardapioRepository {
    Cardapio salvar(Cardapio cardapio);
    Optional<Cardapio> findById(Long id);
    Page<Cardapio> findByRestauranteId(Long restauranteId, Pageable pageable);
    void deletar(Long id);
}