package br.com.fiap.fasefood.application.usecase.cardapio;

import br.com.fiap.fasefood.core.domain.entities.Cardapio;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioRepository;
import br.com.fiap.fasefood.core.usecase.interfaces.cardapio.ListarTodosCardapioUseCase;
import br.com.fiap.fasefood.infra.controller.dto.ListCardapioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListarTodosCardapioUseCaseImpl implements ListarTodosCardapioUseCase {

    private final CardapioRepository cardapioRepository;

    public ListarTodosCardapioUseCaseImpl(CardapioRepository cardapioRepository) {
        this.cardapioRepository = cardapioRepository;
    }

    @Override
    public Page<ListCardapioDTO> listar(Pageable pageable) {
        Page<Cardapio> cardapios = cardapioRepository.listarTodos(pageable);
        return cardapios.map(ListCardapioDTO::new);
    }
}
