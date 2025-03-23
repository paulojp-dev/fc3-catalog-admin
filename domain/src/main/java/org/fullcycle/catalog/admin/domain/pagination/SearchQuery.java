package org.fullcycle.catalog.admin.domain.pagination;

public record SearchQuery(
    Integer page,
    Integer quantityPerPage,
    String whereFilterTerms,
    String sortField,
    String sortDirection
) {

    public SearchQuery {
        if (page == null || page < 0) {
            throw new IllegalArgumentException("Page must be a non-null, non-negative integer");
        }
        if (quantityPerPage == null || quantityPerPage <= 0) {
            throw new IllegalArgumentException("Quantity per page must be a non-null, positive integer");
        }
    }
}
