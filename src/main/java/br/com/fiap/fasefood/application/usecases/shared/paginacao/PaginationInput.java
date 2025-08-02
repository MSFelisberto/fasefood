package br.com.fiap.fasefood.application.usecases.shared.paginacao;

public record PaginationInput(
        int page,
        int size,
        String sortField,
        String sortDirection
) {
}
