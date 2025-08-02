package br.com.fiap.fasefood.application.usecases.restaurante.criar;

import br.com.fiap.fasefood.application.usecases.restaurante.RestauranteOutput;
import br.com.fiap.fasefood.application.usecases.usuario.UsuarioOutput;
import br.com.fiap.fasefood.core.entities.Endereco;
import br.com.fiap.fasefood.core.entities.Restaurante;
import br.com.fiap.fasefood.core.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.RegraDeNegocioException;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.gateways.RestauranteRepository;
import br.com.fiap.fasefood.core.gateways.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class CriarRestauranteUseCaseImpl implements CriarRestauranteUseCase {

    private final RestauranteRepository restauranteRepository;
    private final UsuarioRepository usuarioRepository;

    public CriarRestauranteUseCaseImpl(
            RestauranteRepository restauranteRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.restauranteRepository = restauranteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public RestauranteOutput criar(CriarRestauranteInput criarRestauranteInput) {
        Usuario dono = usuarioRepository.findById(criarRestauranteInput.donoId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com ID: " + criarRestauranteInput.donoId() + " não encontrado."));

        if (!"DONO_RESTAURANTE".equals(dono.getTipoUsuario().getNome())) {
            throw new RegraDeNegocioException("Apenas usuários do tipo DONO_RESTAURANTE podem ser donos de um restaurante.");
        }


        Restaurante novoRestaurante = restauranteRepository.salvar(
                Restaurante.create(
                        null,
                        criarRestauranteInput.nome(),
                        Endereco.criarEndereco(
                                null,
                                criarRestauranteInput.endereco().logradouro(),
                                criarRestauranteInput.endereco().numero(),
                                criarRestauranteInput.endereco().cep(),
                                criarRestauranteInput.endereco().complemento(),
                                criarRestauranteInput.endereco().bairro(),
                                criarRestauranteInput.endereco().cidade(),
                                criarRestauranteInput.endereco().uf()
                        ),
                        criarRestauranteInput.tipoCozinha(),
                        criarRestauranteInput.horarioAbertura(),
                        criarRestauranteInput.horarioFechamento(),
                        dono,
                        true
                )
        );

        return new RestauranteOutput(
                novoRestaurante.getId(),
                novoRestaurante.getNome(),
                novoRestaurante.getEndereco(),
                novoRestaurante.getTipoCozinha(),
                novoRestaurante.getHorarioAbertura(),
                novoRestaurante.getHorarioFechamento(),
                new UsuarioOutput(
                        novoRestaurante.getDono().getId(),
                        novoRestaurante.getDono().getNome(),
                        novoRestaurante.getDono().getEmail(),
                        novoRestaurante.getDono().getLogin()
                )
        );
    }
}