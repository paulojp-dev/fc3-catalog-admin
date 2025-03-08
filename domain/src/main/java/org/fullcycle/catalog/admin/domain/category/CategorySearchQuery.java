package org.fullcycle.catalog.admin.domain.category;

public record CategorySearchQuery(
        Integer page,
        Integer perPage,
        String terms,
        String sort,
        String direction
) {
}
