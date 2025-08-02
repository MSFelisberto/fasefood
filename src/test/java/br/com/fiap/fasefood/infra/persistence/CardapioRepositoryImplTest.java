package br.com.fiap.fasefood.infra.persistence;

import br.com.fiap.fasefood.core.domain.entities.Cardapio;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.infra.controller.mapper.cardapio.CardapioEntityMapper;
import br.com.fiap.fasefood.infra.persistence.entities.CardapioEntity;
import br.com.fiap.fasefood.infra.persistence.jpa.CardapioJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class CardapioRepositoryImplTest {

    private CardapioRepositoryImpl cardapioRepository;
    private CardapioJpaRepository jpaRepository;

    private static final Long CARDAPIO_ID = 1L;
    private Cardapio cardapio;
    private CardapioEntity cardapioEntity;
    private CardapioEntity cardapioEntitySalvo;
    private Cardapio cardapioSalvo;


    @BeforeEach
    public void setUp() {
        jpaRepository = mock(CardapioJpaRepository.class);
        cardapioRepository = new CardapioRepositoryImpl(jpaRepository);
        cardapio = mock(Cardapio.class);
        cardapioEntity = mock(CardapioEntity.class);
        cardapioSalvo = mock(Cardapio.class);
        cardapioEntitySalvo = mock(CardapioEntity.class);

    }

    @Test
    public void deveSalvarCardapioComSucesso(){
        try(MockedStatic<CardapioEntityMapper> cardapioMapper = mockStatic(CardapioEntityMapper.class)) {
            cardapioMapper.when(()-> CardapioEntityMapper.toEntity(cardapio)).thenReturn(cardapioEntity);
            when(jpaRepository.save(cardapioEntity)).thenReturn(cardapioEntitySalvo);
            cardapioMapper.when(()-> CardapioEntityMapper.toDomain(cardapioEntitySalvo)).thenReturn(cardapioSalvo);
            Cardapio response = cardapioRepository.salvar(cardapio);

            assertNotNull(response);
            assertEquals(response, cardapioSalvo);
            verify(jpaRepository,times(1)).save(cardapioEntity);
        }
    }

    @Test
    public void deveRetornarCardapioByID(){
        try(MockedStatic<CardapioEntityMapper> cardapioMapper = mockStatic(CardapioEntityMapper.class)) {
            Optional<Cardapio> cardapioOptional = Optional.of(cardapio);
            when(jpaRepository.findByIdAndAtivoTrue(CARDAPIO_ID)).thenReturn(Optional.of(cardapioEntity));
            cardapioMapper.when(()-> CardapioEntityMapper.toDomain(cardapioEntity)).thenReturn(cardapio);

            Optional<Cardapio> response = cardapioRepository.findById(CARDAPIO_ID);

            assertNotNull(response);
            assertEquals(response, cardapioOptional);
            verify(jpaRepository,times(1)).findByIdAndAtivoTrue(CARDAPIO_ID);
        }
    }

    @Test
    public void deveRetornarCardapioComSucessoByRestauranteID(){
        try(MockedStatic<CardapioEntityMapper> cardapioMapper = mockStatic(CardapioEntityMapper.class)) {
            Long restauranteID = 1L;

            Pageable pageable = PageRequest.of(0, 10);
            Page<CardapioEntity> cardapioEntityPage = new PageImpl<>(List.of(cardapioEntity));
            Page<Cardapio> cardapioPage = new PageImpl<>(List.of(cardapio));

            when(jpaRepository.findAllByRestauranteIdAndAtivoTrue(restauranteID,pageable)).thenReturn(cardapioEntityPage);
            cardapioMapper.when(()-> CardapioEntityMapper.toDomain(cardapioEntity)).thenReturn(cardapio);

            Page<Cardapio> response = cardapioRepository.findByRestauranteId(restauranteID, pageable);

            assertNotNull(response);
            assertEquals(response, cardapioPage);
            verify(jpaRepository,times(1)).findAllByRestauranteIdAndAtivoTrue(restauranteID,pageable);
        }
    }


    @Test
    public void deveLancarExcecaoQuandoCardapioNaoExistir() {
        when(cardapioRepository.findById(CARDAPIO_ID)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> cardapioRepository.deletar(CARDAPIO_ID));

        assertEquals("Cardápio com ID: 1 não encontrado para deleção.", ex.getMessage());
        verify(jpaRepository, never()).save(any());
    }

    @Test
    void deveDesativarESalvarQuandoCardapioExistir() {
        try(MockedStatic<CardapioEntityMapper> cardapioMapper = mockStatic(CardapioEntityMapper.class)) {

            when(jpaRepository.findByIdAndAtivoTrue(CARDAPIO_ID)).thenReturn(Optional.of(cardapioEntity));
            cardapioMapper.when(() -> CardapioEntityMapper.toDomain(cardapioEntity)).thenReturn(cardapioSalvo);
            cardapioRepository.deletar(CARDAPIO_ID);

            verify(jpaRepository,times(1)).findByIdAndAtivoTrue(CARDAPIO_ID);
        }
    }
}
