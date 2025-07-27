package br.com.fiap.fasefood.application.usecase.restaurante;

import br.com.fiap.fasefood.core.domain.entities.Endereco;
import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.core.domain.entities.restaurante.Restaurante;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.exceptions.restaurante.UsuarioNaoPodeCriarRestauranteException;
import br.com.fiap.fasefood.core.usecase.gateways.RestauranteRepository;
import br.com.fiap.fasefood.core.usecase.gateways.UsuarioRepository;
import br.com.fiap.fasefood.core.usecase.interfaces.restaurante.CriarRestauranteUsecase;
import br.com.fiap.fasefood.infra.controller.dto.restaurante.CriarRestauranteDTO;
import org.springframework.stereotype.Service;

@Service
public class CriarRestauranteUseCaseImpl implements CriarRestauranteUsecase {
    private final RestauranteRepository restauranteRepository;
    private final UsuarioRepository usuarioRepository;

    public CriarRestauranteUseCaseImpl(
            RestauranteRepository restauranteRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.restauranteRepository = restauranteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Restaurante criarRestaurante(CriarRestauranteDTO data) {
        Usuario usuario = this.usuarioRepository.findByEmail(data.emailUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        if(usuario.getTipoUsuario().getId() != 1) {
            throw new UsuarioNaoPodeCriarRestauranteException("Apenas administradores podem criar restaurantes");
        }

        return this.restauranteRepository.create(Restaurante.createRestaurante(
                data.nome(),
                new Endereco(
                        null,
                        data.endereco().logradouro(),
                        data.endereco().numero(),
                        data.endereco().cep(),
                        data.endereco().complemento(),
                        data.endereco().bairro(),
                        data.endereco().cidade(),
                        data.endereco().uf()
                ),
                data.tipoCozinha(),
                data.horarioFuncionamento(),
                usuario
        ));
    }
}
