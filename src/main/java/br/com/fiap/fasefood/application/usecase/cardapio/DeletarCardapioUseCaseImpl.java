package br.com.fiap.fasefood.application.usecase.cardapio;

import br.com.fiap.fasefood.core.domain.entities.Cardapio;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioRepository;
import br.com.fiap.fasefood.core.usecase.interfaces.cardapio.DeletarCardapioUseCase;
import org.springframework.stereotype.Service;

@Service
public class DeletarCardapioUseCaseImpl implements DeletarCardapioUseCase{

    private final CardapioRepository cardapioRepository;

    public DeletarCardapioUseCaseImpl(CardapioRepository cardapioRepository) {
        this.cardapioRepository = cardapioRepository;
    }

    @Override
    public void deletar(Long id) {
        Cardapio cardapio = cardapioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cardápio não encontrado com ID: " + id));

        cardapioRepository.deletar(cardapio);
    }
}
