package br.com.fiap.fasefood.core.gateways;

import br.com.fiap.fasefood.application.usecases.shared.paginacao.PageOutput;
import br.com.fiap.fasefood.application.usecases.shared.paginacao.PaginationInput;
import br.com.fiap.fasefood.core.entities.Cardapio;
import java.util.Optional;

public interface CardapioRepository {
    Cardapio salvar(Cardapio cardapio);
    Optional<Cardapio> findById(Long id);
    PageOutput<Cardapio> findByRestauranteId(Long restauranteId, PaginationInput pageable);
    void deletar(Long id);
}