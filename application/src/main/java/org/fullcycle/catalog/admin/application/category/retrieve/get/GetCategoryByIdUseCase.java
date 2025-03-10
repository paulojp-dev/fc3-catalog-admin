package org.fullcycle.catalog.admin.application.category.retrieve.get;

import org.fullcycle.catalog.admin.application.base.UseCase;
import org.fullcycle.catalog.admin.application.exception.ResourceNotFoundException;
import org.fullcycle.catalog.admin.domain.category.CategoryGateway;
import org.fullcycle.catalog.admin.domain.category.CategoryID;

import java.util.function.Supplier;

public class GetCategoryByIdUseCase extends UseCase<String, GetCategoryByIdOutput> {

    private CategoryGateway categoryGateway;

    public GetCategoryByIdUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public GetCategoryByIdOutput execute(final String id) {
        final var category = categoryGateway.findById(CategoryID.of(id))
                .orElseThrow(throwNotFoundException(id));
        return GetCategoryByIdOutput.from(category);
    }

    private static Supplier<ResourceNotFoundException> throwNotFoundException(String id) {
        return () -> ResourceNotFoundException.byId("Category", id);
    }
}
