package br.com.fiap.fasefood.application.usecase.cardapio;

import br.com.fiap.fasefood.core.domain.entities.Cardapio;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioRepository;
import br.com.fiap.fasefood.core.usecase.interfaces.cardapio.BuscarCardapioPorIdUseCase;
import br.com.fiap.fasefood.infra.controller.dto.ListCardapioDTO;
import org.springframework.stereotype.Service;

@Service
public class BuscarCardapioPorIdUseCaseImpl implements BuscarCardapioPorIdUseCase {

    private final CardapioRepository cardapioRepository;

    public BuscarCardapioPorIdUseCaseImpl(CardapioRepository cardapioRepository) {
        this.cardapioRepository = cardapioRepository;
    }

    @Override
    public ListCardapioDTO buscarPorId(Long id) {
        Cardapio cardapio = cardapioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cardápio não encontrado com ID: " + id));

        return new ListCardapioDTO(cardapio);
    }
}
