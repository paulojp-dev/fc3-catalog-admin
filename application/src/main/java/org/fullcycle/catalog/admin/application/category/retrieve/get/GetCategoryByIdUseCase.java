package org.fullcycle.catalog.admin.application.category.retrieve.get;

import org.fullcycle.catalog.admin.application.base.UseCase;
import org.fullcycle.catalog.admin.application.exception.CategoryNotFoundException;
import org.fullcycle.catalog.admin.domain.category.CategoryGateway;
import org.fullcycle.catalog.admin.domain.base.ID;

import java.util.function.Supplier;

public class GetCategoryByIdUseCase extends UseCase<String, GetCategoryByIdOutput> {

    private final CategoryGateway categoryGateway;

    public GetCategoryByIdUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public GetCategoryByIdOutput execute(final String id) {
        final var category = categoryGateway.findById(ID.of(id))
                .orElseThrow(notFoundException(id));
        return GetCategoryByIdOutput.from(category);
    }

    private static Supplier<CategoryNotFoundException> notFoundException(String id) {
        return () -> CategoryNotFoundException.byId(id);
    }
}
