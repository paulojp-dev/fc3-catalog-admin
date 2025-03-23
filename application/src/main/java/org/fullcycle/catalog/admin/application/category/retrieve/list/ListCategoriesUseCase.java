package org.fullcycle.catalog.admin.application.category.retrieve.list;

import org.fullcycle.catalog.admin.application.base.UseCase;
import org.fullcycle.catalog.admin.domain.category.CategoryGateway;
import org.fullcycle.catalog.admin.domain.pagination.Pagination;
import org.fullcycle.catalog.admin.domain.pagination.SearchQuery;

public class ListCategoriesUseCase extends UseCase<SearchQuery, Pagination<ListCategoriesOutput>> {

    private final CategoryGateway categoryGateway;

    public ListCategoriesUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public Pagination<ListCategoriesOutput> execute(SearchQuery query) {
        return categoryGateway.findAll(query)
            .map(ListCategoriesOutput::from);
    }
}
