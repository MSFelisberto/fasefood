package br.com.fiap.fasefood.application.usecase.restaurante;

import br.com.fiap.fasefood.core.domain.entities.Restaurante;
import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.RegraDeNegocioException;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.RestauranteRepository;
import br.com.fiap.fasefood.core.usecase.gateways.UsuarioRepository;
import br.com.fiap.fasefood.core.usecase.interfaces.restaurante.CriarRestauranteUseCase;
import br.com.fiap.fasefood.infra.controller.dto.restaurante.CreateRestauranteDTO;
import br.com.fiap.fasefood.infra.controller.dto.restaurante.RestauranteResponseDTO;
import br.com.fiap.fasefood.infra.controller.mapper.restaurante.RestauranteMapper;
import org.springframework.stereotype.Service;

@Service
public class CriarRestauranteUseCaseImpl implements CriarRestauranteUseCase {

    private final RestauranteRepository restauranteRepository;
    private final UsuarioRepository usuarioRepository;

    public CriarRestauranteUseCaseImpl(RestauranteRepository restauranteRepository, UsuarioRepository usuarioRepository) {
        this.restauranteRepository = restauranteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public RestauranteResponseDTO criar(CreateRestauranteDTO dto) {
        Usuario dono = usuarioRepository.findById(dto.donoId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com ID: " + dto.donoId() + " não encontrado."));

        if (Boolean.FALSE.equals("DONO_RESTAURANTE".equals(dono.getTipoUsuario().getNome()))) {
            throw new RegraDeNegocioException("Apenas usuários do tipo DONO_RESTAURANTE podem ser donos de um restaurante.");
        }

        Restaurante restaurante = RestauranteMapper.toDomain(dto, dono);
        Restaurante novoRestaurante = restauranteRepository.salvar(restaurante);

        return RestauranteMapper.toResponseDTO(novoRestaurante);
    }
}