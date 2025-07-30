package br.com.fiap.fasefood.application.usecase.restaurante.atualizar;

import br.com.fiap.fasefood.core.domain.entities.Restaurante;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.RestauranteRepository;
import br.com.fiap.fasefood.infra.controller.dto.restaurante.UpdateRestauranteDTO;
import br.com.fiap.fasefood.infra.controller.dto.restaurante.RestauranteResponseDTO;
import br.com.fiap.fasefood.infra.controller.mapper.restaurante.RestauranteMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AtualizarRestauranteUseCaseImpl implements AtualizarRestauranteUseCase {

    private final RestauranteRepository restauranteRepository;

    public AtualizarRestauranteUseCaseImpl(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    @Override
    @Transactional
    public RestauranteResponseDTO atualizar(Long id, UpdateRestauranteDTO dto) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado com ID: " + id));

        restaurante.atualizarInformacoes(
                dto.nome(),
                dto.tipoCozinha(),
                dto.horarioAbertura(),
                dto.horarioFechamento()
        );

        if (dto.endereco() != null) {
            restaurante.getEndereco().atualizar(
                    dto.endereco().logradouro(),
                    dto.endereco().numero(),
                    dto.endereco().cep(),
                    dto.endereco().complemento(),
                    dto.endereco().bairro(),
                    dto.endereco().cidade(),
                    dto.endereco().uf()
            );
        }

        Restaurante restauranteAtualizado = restauranteRepository.salvar(restaurante);
        return RestauranteMapper.toResponseDTO(restauranteAtualizado);
    }
}