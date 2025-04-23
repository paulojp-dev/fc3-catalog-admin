package org.fullcycle.catalog.admin.application.category.retrieve.get;

import org.fullcycle.catalog.admin.application.base.UseCase;
import org.fullcycle.catalog.admin.domain.category.Category;
import org.fullcycle.catalog.admin.domain.category.CategoryGateway;
import org.fullcycle.catalog.admin.domain.category.CategoryID;
import org.fullcycle.catalog.admin.domain.exception.NotFoundException;

import java.util.function.Supplier;

public class GetCategoryByIdUseCase extends UseCase<String, GetCategoryByIdOutput> {

    private final CategoryGateway categoryGateway;

    public GetCategoryByIdUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public GetCategoryByIdOutput execute(final String id) {
        final var categoryId = CategoryID.of(id);
        final var category = categoryGateway.findById(categoryId)
            .orElseThrow(notFoundException(categoryId));
        return GetCategoryByIdOutput.from(category);
    }

    private static Supplier<NotFoundException> notFoundException(CategoryID id) {
        return () -> NotFoundException.with(Category.class, id);
    }
}
