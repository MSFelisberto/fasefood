package br.com.fiap.fasefood.application.usecases.shared.paginacao;

import java.util.List;

public record PageOutput<T>(
        List<T> content,
        int currentPage,
        int size,
        int totalPages,
        long totalElements
) {
}
