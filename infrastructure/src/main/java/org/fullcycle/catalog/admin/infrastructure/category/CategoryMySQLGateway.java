package org.fullcycle.catalog.admin.infrastructure.category;

import org.fullcycle.catalog.admin.domain.category.Category;
import org.fullcycle.catalog.admin.domain.category.CategoryGateway;
import org.fullcycle.catalog.admin.domain.category.CategoryID;
import org.fullcycle.catalog.admin.domain.category.CategorySearchQuery;
import org.fullcycle.catalog.admin.domain.pagination.Pagination;
import org.fullcycle.catalog.admin.infrastructure.category.persistence.CategoryJpaEntity;
import org.fullcycle.catalog.admin.infrastructure.category.persistence.CategoryJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CategoryMySQLGateway implements CategoryGateway {

    private final CategoryJpaRepository repository;

    public CategoryMySQLGateway(final CategoryJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category create(final Category category) {
        return save(category);
    }

    @Override
    public Optional<Category> findById(final CategoryID id) {
        final var result = repository.findById(id.getValue());
        return result.map(CategoryJpaEntity::toDomain);
    }

    @Override
    public Pagination<Category> findAll(final CategorySearchQuery searchQuery) {
        return null;
    }

    @Override
    public Category update(final Category category) {
        return save(category);
    }

    private Category save(final Category category) {
        final var categoryJpa = CategoryJpaEntity.from(category);
        return repository.save(categoryJpa).toDomain();
    }

    @Override
    public void deleteById(final CategoryID id) {
        repository.deleteById(id.getValue());
    }
}
