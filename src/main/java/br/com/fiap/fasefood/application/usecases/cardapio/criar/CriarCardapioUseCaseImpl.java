package br.com.fiap.fasefood.application.usecases.cardapio.criar;

import br.com.fiap.fasefood.core.entities.Cardapio;
import br.com.fiap.fasefood.core.entities.CardapioItem;
import br.com.fiap.fasefood.core.entities.Restaurante;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.gateways.CardapioItemRepository;
import br.com.fiap.fasefood.core.gateways.CardapioRepository;
import br.com.fiap.fasefood.core.gateways.RestauranteRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public class CriarCardapioUseCaseImpl implements CriarCardapioUseCase {

    private final CardapioRepository cardapioRepository;
    private final RestauranteRepository restauranteRepository;
    private final CardapioItemRepository cardapioItemRepository;

    public CriarCardapioUseCaseImpl(CardapioRepository cardapioRepository, RestauranteRepository restauranteRepository, CardapioItemRepository cardapioItemRepository) {
        this.cardapioRepository = cardapioRepository;
        this.restauranteRepository = restauranteRepository;
        this.cardapioItemRepository = cardapioItemRepository;
    }

    @Override
    @Transactional
    public CriarCardapioOutput criar(CriarCardapioInput criarCardapioInput) {
        Restaurante restaurante = restauranteRepository.findById(criarCardapioInput.restauranteId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante com ID: " + criarCardapioInput.restauranteId() + " não encontrado."));

        Cardapio novoCardapio = cardapioRepository.salvar(Cardapio.create(
                null,
                restaurante,
                criarCardapioInput.nome(),
                criarCardapioInput.descricao(),
                true
        ));

        List<CardapioItem> itensSalvos = new ArrayList<>();
        if (criarCardapioInput.itens() != null && !criarCardapioInput.itens().isEmpty()) {

            for (CriarCardapioItemInput item : criarCardapioInput.itens()) {
                itensSalvos.add(
                        cardapioItemRepository.salvar(
                                CardapioItem.create(
                                        null,
                                        novoCardapio,
                                        item.nome(),
                                        item.descricao(),
                                        item.preco(),
                                        item.apenasNoLocal(),
                                        item.caminhoFoto(),
                                        true
                                )
                        )
                );
            }
        }
        novoCardapio.setItens(itensSalvos);

        return new CriarCardapioOutput(
                novoCardapio.getId()
        );
    }
}