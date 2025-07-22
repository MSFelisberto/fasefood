package br.com.fiap.fasefood.application.usecase.cardapio;

import br.com.fiap.fasefood.core.domain.entities.Cardapio;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioRepository;
import br.com.fiap.fasefood.core.usecase.interfaces.cardapio.AtualizarCardapioUseCase;
import br.com.fiap.fasefood.infra.controller.dto.ListCardapioDTO;
import br.com.fiap.fasefood.infra.controller.dto.UpdateCardapioDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AtualizarCardapioUseCaseImpl implements AtualizarCardapioUseCase {
    private final CardapioRepository cardapioRepository;

    public AtualizarCardapioUseCaseImpl(CardapioRepository cardapioRepository) {
        this.cardapioRepository = cardapioRepository;
    }

    @Override
    @Transactional
    public ListCardapioDTO atualizar(Long id, UpdateCardapioDTO dados) {
        Cardapio cardapio = cardapioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cardápio não encontrado com ID: " + id));

        cardapio.atualizar(
                dados.nome(),
                dados.descricao());

        Cardapio atualizado = cardapioRepository.salvar(cardapio);

        return new ListCardapioDTO(atualizado);
    }
}
