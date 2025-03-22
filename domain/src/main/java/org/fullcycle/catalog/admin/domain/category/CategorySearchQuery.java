package org.fullcycle.catalog.admin.domain.category;

public record CategorySearchQuery(
        Integer page,
        Integer quantityPerPage,
        String whereFilterTerms,
        String sortField,
        String sortDirection
) {
}
