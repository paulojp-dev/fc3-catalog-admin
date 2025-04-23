package org.fullcycle.catalog.admin.domain.category;

import org.fullcycle.catalog.admin.domain.pagination.Pagination;
import org.fullcycle.catalog.admin.domain.pagination.SearchQuery;

import java.util.Optional;

public interface CategoryGateway {

    Category create(Category category);

    Optional<Category> findById(CategoryID id);

    Pagination<Category> findAll(SearchQuery searchQuery);

    Category update(Category category);

    void deleteById(CategoryID id);
}
