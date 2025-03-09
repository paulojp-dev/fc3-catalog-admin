package org.fullcycle.catalog.admin.domain.category;

import org.fullcycle.catalog.admin.domain.pagination.Pagination;

import java.util.Optional;

public interface CategoryGateway {

    public Category create(Category category);

    public Optional<Category> findById(CategoryID id);

    public Pagination<Category> findAll(CategorySearchQuery searchQuery);

    public Category update(Category category);

    public void deleteById(CategoryID id);
}
