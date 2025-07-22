package br.com.fiap.fasefood.core.usecase.gateways;

import br.com.fiap.fasefood.core.domain.entities.Cardapio;
import br.com.fiap.fasefood.core.domain.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CardapioRepository {
    Optional<Cardapio> findById(Long id);
    List<Cardapio> findAll();
    Optional<Cardapio> findByNome(String nome);
    Cardapio salvar(Cardapio cardapio);
    Page<Cardapio> listarTodos(Pageable paginacao);
    void deletar(Cardapio cardapio);
}