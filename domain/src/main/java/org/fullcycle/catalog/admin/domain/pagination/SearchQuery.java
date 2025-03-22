package org.fullcycle.catalog.admin.domain.pagination;

public record SearchQuery(
        Integer page,
        Integer quantityPerPage,
        String whereFilterTerms,
        String sortField,
        String sortDirection
) {
}
