package br.com.fiap.fasefood.application.usecase.cardapio;

import br.com.fiap.fasefood.core.domain.entities.Cardapio;
import br.com.fiap.fasefood.core.exceptions.ResourceAlreadyExists;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioRepository;
import br.com.fiap.fasefood.core.usecase.interfaces.cardapio.CriarCardapioUseCase;
import br.com.fiap.fasefood.infra.controller.dto.CreateCardapioDTO;
import br.com.fiap.fasefood.infra.controller.mapper.CardapioMapper;
import org.springframework.stereotype.Service;

@Service
public class CriarCardapioUseCaseImpl implements CriarCardapioUseCase{

    private final CardapioRepository cardapioRepository;

    public CriarCardapioUseCaseImpl(CardapioRepository cardapioRepository) {
        this.cardapioRepository = cardapioRepository;
    }

    @Override
    public Cardapio criarCardapio(CreateCardapioDTO dto) {
        if (cardapioRepository.findByNome(dto.nome()).isPresent()) {
            throw new ResourceAlreadyExists("Cardápio já cadastrado");
        }
        Cardapio cardapio = CardapioMapper.toDomain(dto);

        return cardapioRepository.salvar(cardapio);
    }
}
