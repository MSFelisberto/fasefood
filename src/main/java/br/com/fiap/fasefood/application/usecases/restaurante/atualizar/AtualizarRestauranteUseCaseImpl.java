package br.com.fiap.fasefood.application.usecases.restaurante.atualizar;

import br.com.fiap.fasefood.application.usecases.restaurante.RestauranteOutput;
import br.com.fiap.fasefood.application.usecases.usuario.UsuarioOutput;
import br.com.fiap.fasefood.core.entities.Restaurante;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.gateways.RestauranteRepository;
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
    public RestauranteOutput atualizar(Long id, UpdateRestauranteInput input) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado com ID: " + id));

        restaurante.atualizarInformacoes(
                input.nome(),
                input.tipoCozinha(),
                input.horarioAbertura(),
                input.horarioFechamento()
        );

        if (input.endereco() != null) {
            restaurante.getEndereco().atualizar(
                    input.endereco().logradouro(),
                    input.endereco().numero(),
                    input.endereco().cep(),
                    input.endereco().complemento(),
                    input.endereco().bairro(),
                    input.endereco().cidade(),
                    input.endereco().uf()
            );
        }

        Restaurante restauranteAtualizado = restauranteRepository.salvar(restaurante);
        return new RestauranteOutput(
                restauranteAtualizado.getId(),
                restauranteAtualizado.getNome(),
                restauranteAtualizado.getEndereco(),
                restauranteAtualizado.getTipoCozinha(),
                restauranteAtualizado.getHorarioAbertura(),
                restauranteAtualizado.getHorarioFechamento(),
                new UsuarioOutput(
                        restauranteAtualizado.getDono().getId(),
                        restauranteAtualizado.getDono().getNome(),
                        restauranteAtualizado.getDono().getEmail(),
                        restauranteAtualizado.getDono().getLogin()
                )
        );
    }
}