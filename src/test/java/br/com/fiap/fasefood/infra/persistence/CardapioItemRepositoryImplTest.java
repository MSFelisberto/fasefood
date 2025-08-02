package br.com.fiap.fasefood.infra.persistence;

import br.com.fiap.fasefood.core.domain.entities.CardapioItem;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.infra.controller.mapper.cardapio.CardapioItemEntityMapper;
import br.com.fiap.fasefood.infra.persistence.entities.CardapioItemEntity;
import br.com.fiap.fasefood.infra.persistence.jpa.CardapioItemJpaRepository;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class CardapioItemRepositoryImplTest {

    private CardapioItemRepositoryImpl cardapioItemRepository;
    private CardapioItemJpaRepository jpaRepository;

    private static final Long CARDAPIO_ITEM_ID = 1L;
    private CardapioItem cardapioItem;
    private CardapioItemEntity entity;
    private CardapioItem cardapioItemSalvo;

    @BeforeEach
    public void setUp() {
        jpaRepository = mock(CardapioItemJpaRepository.class);
        cardapioItemRepository = new CardapioItemRepositoryImpl(jpaRepository);

        cardapioItem = mock(CardapioItem.class);
        entity = mock(CardapioItemEntity.class);
        cardapioItemSalvo = mock(CardapioItem.class);
    }

    @Test
    public void deveSalvarCardapioItemComSucesso(){

        try(MockedStatic<CardapioItemEntityMapper> cardapioItemEntityMapper = mockStatic(CardapioItemEntityMapper.class)) {

            CardapioItemEntity cardapioItemEntity = mock(CardapioItemEntity.class);

            cardapioItemEntityMapper.when(() -> CardapioItemEntityMapper.toEntity(cardapioItem)).thenReturn(cardapioItemEntity);
            when(jpaRepository.save(cardapioItemEntity)).thenReturn(entity);
            cardapioItemEntityMapper.when(() -> CardapioItemEntityMapper.toDomain(entity)).thenReturn(cardapioItemSalvo);

            CardapioItem response = cardapioItemRepository.salvar(cardapioItem);
            assertEquals(response, cardapioItemSalvo);
            assertNotNull(response);
        }
    }

    @Test
    public void deveRetornarCardapioItemByID(){
        try(MockedStatic<CardapioItemEntityMapper> cardapioItemEntityMapper = mockStatic(CardapioItemEntityMapper.class)) {

            Optional<CardapioItem> optionalCardapioItem = Optional.of(cardapioItemSalvo);
            when(jpaRepository.findByIdAndAtivoTrue(CARDAPIO_ITEM_ID)).thenReturn(Optional.of(entity));
            cardapioItemEntityMapper.when(() -> CardapioItemEntityMapper.toDomain(entity)).thenReturn(cardapioItemSalvo);
            Optional<CardapioItem> response = cardapioItemRepository.findById(CARDAPIO_ITEM_ID);

            assertEquals(response, optionalCardapioItem);
            assertNotNull(response);
            assertTrue(response.isPresent());
        }
    }

    @Test
    public void deveRetornarCardapioItemByCardapioID(){
        try(MockedStatic<CardapioItemEntityMapper> cardapioItemEntityMapper = mockStatic(CardapioItemEntityMapper.class)) {

            Pageable pageable = PageRequest.of(0, 10);
            Page<CardapioItemEntity> pageEntity = new PageImpl<>(List.of(entity));
            Page<CardapioItem> page = new PageImpl<>(List.of(cardapioItem));

            Long cardapioId = 1L;

            when(jpaRepository.findAllByCardapioIdAndAtivoTrue(cardapioId, pageable)).thenReturn(pageEntity);
            cardapioItemEntityMapper.when(()-> CardapioItemEntityMapper.toDomain(entity)).thenReturn(cardapioItem);

            Page<CardapioItem> response = cardapioItemRepository.findByCardapioId(cardapioId, pageable);

            assertNotNull(response);
            assertEquals(1, response.getTotalElements());
            assertEquals(page,response);
        }
    }


    @Test
    public void deveLancarExcecaoQuandoCardapioItemNaoExistir() {
        when(cardapioItemRepository.findById(CARDAPIO_ITEM_ID)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> cardapioItemRepository.deletar(CARDAPIO_ITEM_ID));

        assertEquals("Item de cardápio com ID: 1 não encontrado para deleção.", ex.getMessage());
        verify(jpaRepository, never()).save(any());
    }

    @Test
    void deveDesativarESalvarQuandoCardapioItemExistir() {
        try(MockedStatic<CardapioItemEntityMapper> cardapioItemEntityMapper = mockStatic(CardapioItemEntityMapper.class)) {

            when(jpaRepository.findByIdAndAtivoTrue(CARDAPIO_ITEM_ID)).thenReturn(Optional.of(entity));
            cardapioItemEntityMapper.when(() -> CardapioItemEntityMapper.toDomain(entity)).thenReturn(cardapioItemSalvo);
            cardapioItemRepository.deletar(CARDAPIO_ITEM_ID);

            verify(jpaRepository,times(1)).findByIdAndAtivoTrue(CARDAPIO_ITEM_ID);
        }
    }
}
