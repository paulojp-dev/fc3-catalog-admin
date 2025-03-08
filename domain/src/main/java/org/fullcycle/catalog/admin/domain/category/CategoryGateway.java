package org.fullcycle.catalog.admin.domain.category;

import org.fullcycle.catalog.admin.domain.pagination.Pagination;

public interface CategoryGateway {

    public Category create(Category category);

    public Category findById(CategoryID id);

    public Pagination<Category> findAll(CategorySearchQuery searchQuery);

    public Category update(Category category);

    public void deleteById(CategoryID id);
}
