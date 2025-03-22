package org.fullcycle.catalog.admin.infrastructure.category;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.fullcycle.catalog.admin.domain.category.Category;
import org.fullcycle.catalog.admin.domain.category.CategoryGateway;
import org.fullcycle.catalog.admin.domain.category.CategoryID;
import org.fullcycle.catalog.admin.domain.category.CategorySearchQuery;
import org.fullcycle.catalog.admin.domain.pagination.Pagination;
import org.fullcycle.catalog.admin.infrastructure.category.persistence.CategoryJpaEntity;
import org.fullcycle.catalog.admin.infrastructure.category.persistence.CategoryJpaRepository;
import org.fullcycle.catalog.admin.infrastructure.utils.SpecificationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
    public Pagination<Category> findAll(final CategorySearchQuery query) {
        final var specification = Optional.ofNullable(query.whereFilterTerms())
            .map(terms -> {
                final var likeName = SpecificationUtil.<CategoryJpaEntity>like(terms, "name");
                final var likeDescription = SpecificationUtil.<CategoryJpaEntity>like(terms, "description");
                return likeName.or(likeDescription);
            }).orElse(SpecificationUtil.conjunction());
        final var sortField = Optional.ofNullable(query.sortField())
            .orElse("createdAt");
        final var sortDirection = Optional.ofNullable(query.sortDirection())
            .map(Sort.Direction::fromString)
            .orElse(Sort.Direction.ASC);
        final var page = PageRequest.of(
            query.page(),
            query.quantityPerPage(),
            Sort.by(sortDirection, sortField)
        );
        Page<CategoryJpaEntity> result = repository.findAll(Specification.where(specification), page);
        return new Pagination<>(
            result.getNumber(),
            result.getSize(),
            result.getTotalElements(),
            result.map(CategoryJpaEntity::toDomain).toList()
        );
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
