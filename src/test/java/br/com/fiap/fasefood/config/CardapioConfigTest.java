package br.com.fiap.fasefood.config;

import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioItemRepository;
import br.com.fiap.fasefood.core.usecase.gateways.CardapioRepository;
import br.com.fiap.fasefood.core.usecase.gateways.RestauranteRepository;
import br.com.fiap.fasefood.infra.persistence.UsuarioRepositoryImpl;
import br.com.fiap.fasefood.infra.persistence.entities.UsuarioEntity;
import br.com.fiap.fasefood.infra.persistence.jpa.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class CardapioConfigTest {

    private CardapioConfig cardapioConfig;

    private CardapioRepository cardapioRepo;
    private RestauranteRepository restauranteRepo;
    private CardapioItemRepository itemRepo;

    @BeforeEach
    public void setUp() {
        cardapioConfig = new CardapioConfig();

        cardapioRepo = mock(CardapioRepository.class);
        restauranteRepo = mock(RestauranteRepository.class);
        itemRepo = mock(CardapioItemRepository.class);
    }

    @Test
    public void deveCriarCardapioUseCase(){
        
    }

}
