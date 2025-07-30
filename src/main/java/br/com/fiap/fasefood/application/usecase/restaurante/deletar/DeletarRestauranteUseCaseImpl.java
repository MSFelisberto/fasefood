package br.com.fiap.fasefood.application.usecase.restaurante.deletar;

import br.com.fiap.fasefood.core.domain.entities.Restaurante;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.RestauranteRepository;
import org.springframework.stereotype.Service;

@Service
public class DeletarRestauranteUseCaseImpl implements DeletarRestauranteUseCase {

    private final RestauranteRepository restauranteRepository;

    public DeletarRestauranteUseCaseImpl(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    @Override
    public void deletar(Long id) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado com ID: " + id));

        restaurante.desativar();

        restauranteRepository.salvar(restaurante);
    }
}