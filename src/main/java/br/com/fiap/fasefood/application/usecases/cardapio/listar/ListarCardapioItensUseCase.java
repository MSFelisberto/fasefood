package br.com.fiap.fasefood.application.usecases.cardapio.listar;

import br.com.fiap.fasefood.application.usecases.cardapio.criar.CriarCardapioItemOutput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListarCardapioItensUseCase {
    Page<CriarCardapioItemOutput> listar(Long cardapioId, Pageable pageable);
}