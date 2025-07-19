package br.com.fiap.fasefood.infra.controller;

import br.com.fiap.fasefood.core.domain.entities.Endereco;
import br.com.fiap.fasefood.core.domain.entities.restaurante.Restaurante;
import br.com.fiap.fasefood.core.usecase.restaurante.CriarRestauranteUseCase;
import br.com.fiap.fasefood.infra.controller.dto.restaurante.CriarRestauranteDTO;
import br.com.fiap.fasefood.infra.persistence.RestauranteRespositoryImpl;
import br.com.fiap.fasefood.infra.persistence.jpa.RestauranteJpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/restaurantes")
public class RestauranteController {

    private final RestauranteJpaRepository restauranteJpaRepository;

    public RestauranteController(RestauranteJpaRepository restauranteJpaRepository) {
        this.restauranteJpaRepository = restauranteJpaRepository;
    }

    @PostMapping
    public void criarRestaurante(
            @RequestBody CriarRestauranteDTO data
    ) {
        CriarRestauranteUseCase usecase = new CriarRestauranteUseCase(new RestauranteRespositoryImpl(this.restauranteJpaRepository));

        usecase.execute(Restaurante.createRestaurante(
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
                data.emailUsuario()
        ));
    }
}
