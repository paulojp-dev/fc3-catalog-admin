package org.fullcycle.catalog.admin.application.category.retrieve.list;

import org.fullcycle.catalog.admin.application.base.UseCase;
import org.fullcycle.catalog.admin.domain.category.CategoryGateway;
import org.fullcycle.catalog.admin.domain.category.CategorySearchQuery;
import org.fullcycle.catalog.admin.domain.pagination.Pagination;

public class ListCategoriesUseCase extends UseCase<CategorySearchQuery, Pagination<ListCategoriesOutput>> {

    private final CategoryGateway categoryGateway;

    public ListCategoriesUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public Pagination<ListCategoriesOutput> execute(CategorySearchQuery query) {
        return categoryGateway.findAll(query)
                .map(ListCategoriesOutput::from);
    }
}
