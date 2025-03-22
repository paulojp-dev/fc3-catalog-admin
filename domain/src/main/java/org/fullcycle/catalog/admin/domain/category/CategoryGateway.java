package org.fullcycle.catalog.admin.domain.category;

import org.fullcycle.catalog.admin.domain.base.ID;
import org.fullcycle.catalog.admin.domain.pagination.Pagination;
import org.fullcycle.catalog.admin.domain.pagination.SearchQuery;

import java.util.Optional;

public interface CategoryGateway {

    public Category create(Category category);

    public Optional<Category> findById(ID id);

    public Pagination<Category> findAll(SearchQuery searchQuery);

    public Category update(Category category);

    public void deleteById(ID id);
}
