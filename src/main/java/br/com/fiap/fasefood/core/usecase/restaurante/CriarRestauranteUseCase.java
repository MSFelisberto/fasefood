package br.com.fiap.fasefood.core.usecase.restaurante;

import br.com.fiap.fasefood.core.domain.entities.Endereco;
import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.core.domain.entities.restaurante.Restaurante;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.RestauranteRepository;
import br.com.fiap.fasefood.core.usecase.gateways.UsuarioRepository;
import br.com.fiap.fasefood.infra.controller.dto.restaurante.CriarRestauranteDTO;

public class CriarRestauranteUseCase {
    private final RestauranteRepository restauranteRepository;
    private final UsuarioRepository usuarioRepository;

    public CriarRestauranteUseCase(
            RestauranteRepository restauranteRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.restauranteRepository = restauranteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Restaurante execute(CriarRestauranteDTO data) {
        Usuario usuario = this.usuarioRepository.findByEmail(data.emailUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

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
