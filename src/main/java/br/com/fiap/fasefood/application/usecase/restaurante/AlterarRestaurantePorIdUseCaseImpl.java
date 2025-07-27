package br.com.fiap.fasefood.application.usecase.restaurante;

import br.com.fiap.fasefood.core.domain.entities.Endereco;
import br.com.fiap.fasefood.core.domain.entities.restaurante.Restaurante;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.RestauranteRepository;
import br.com.fiap.fasefood.core.usecase.interfaces.restaurante.AlterarRestaurantePorIdUseCase;
import br.com.fiap.fasefood.core.usecase.interfaces.restaurante.BuscarRestaurantePorIdUseCase;
import br.com.fiap.fasefood.infra.controller.dto.restaurante.UpdateRestauranteDTO;
import br.com.fiap.fasefood.infra.controller.mapper.EnderecoEntityMapper;
import org.springframework.stereotype.Service;

@Service
public class AlterarRestaurantePorIdUseCaseImpl implements AlterarRestaurantePorIdUseCase {
    private final RestauranteRepository restauranteRepository;

    public AlterarRestaurantePorIdUseCaseImpl(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    @Override
    public Restaurante alterarPorId(Long id, UpdateRestauranteDTO dto) {
        Restaurante restaurante = this.restauranteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado com ID: " + id));

        return this.restauranteRepository.updateById(id, Restaurante.createRestaurante(
                dto.nome(),
                EnderecoEntityMapper.toDomain(dto.endereco(), restaurante.getId()),
                dto.tipoCozinha(),
                dto.horarioFuncionamento(),
                restaurante.getUsuario()
        ));
    }
}
