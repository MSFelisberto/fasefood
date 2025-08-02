package br.com.fiap.fasefood.application.usecases.restaurante.deletar;

import br.com.fiap.fasefood.core.entities.Restaurante;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.gateways.RestauranteRepository;


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