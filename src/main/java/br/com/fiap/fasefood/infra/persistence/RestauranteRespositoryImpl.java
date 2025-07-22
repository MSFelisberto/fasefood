package br.com.fiap.fasefood.infra.persistence;

import br.com.fiap.fasefood.core.domain.entities.restaurante.Restaurante;
import br.com.fiap.fasefood.core.usecase.gateways.RestauranteRepository;
import br.com.fiap.fasefood.infra.controller.mapper.RestauranteMapper;
import br.com.fiap.fasefood.infra.controller.mapper.UserEntityMapper;
import br.com.fiap.fasefood.infra.persistence.entities.EnderecoEntity;
import br.com.fiap.fasefood.infra.persistence.entities.RestauranteEntity;
import br.com.fiap.fasefood.infra.persistence.entities.TipoUsuarioEntity;
import br.com.fiap.fasefood.infra.persistence.entities.UsuarioEntity;
import br.com.fiap.fasefood.infra.persistence.jpa.RestauranteJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RestauranteRespositoryImpl implements RestauranteRepository {
    private final RestauranteJpaRepository restauranteJpaRepository;

    public RestauranteRespositoryImpl(RestauranteJpaRepository restauranteJpaRepository) {
        this.restauranteJpaRepository = restauranteJpaRepository;
    }

    @Override
    public Restaurante create(Restaurante restaurante) {

        RestauranteEntity restauranteEntity = this.restauranteJpaRepository.save(
                new RestauranteEntity(
                        restaurante.getNome(),
                        new EnderecoEntity(restaurante.getEndereco()),
                        restaurante.getTipoCozinha(),
                        restaurante.getHorarioFuncionamento(),
                        new UsuarioEntity(
                                restaurante.getUsuario().getId(),
                                restaurante.getUsuario().getNome(),
                                restaurante.getUsuario().getEmail(),
                                restaurante.getUsuario().getLogin(),
                                restaurante.getUsuario().getSenha(),
                                restaurante.getUsuario().getDataUltimaAtualizacao(),
                                new TipoUsuarioEntity(
                                        restaurante.getUsuario().getTipoUsuario().getId(),
                                        restaurante.getUsuario().getTipoUsuario().getNome()
                                ),
                                new EnderecoEntity(restaurante.getUsuario().getEndereco()),
                                restaurante.getUsuario().isAtivo()
                        )
                )
        );

        restaurante.setId(restauranteEntity.getId());
        return restaurante;
    }

    @Override
    public Page<Restaurante> findAll(Pageable pageable) {
        Page<RestauranteEntity> restauranteEntity = this.restauranteJpaRepository.findAll(pageable);
        return restauranteEntity.map(RestauranteMapper::toDomain);

    }

    @Override
    public Optional<Restaurante> findById(Long id) {
        return this.restauranteJpaRepository.findById(id).map(RestauranteMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        this.restauranteJpaRepository.deleteById(id);
    }

    @Override
    public Restaurante updateById(Long id, Restaurante restaurante) {
        restaurante.setId(id);

        RestauranteEntity restauranteEntity = RestauranteMapper.toEntity(restaurante);
        this.restauranteJpaRepository.save(restauranteEntity);
        return restaurante;
    }

}
