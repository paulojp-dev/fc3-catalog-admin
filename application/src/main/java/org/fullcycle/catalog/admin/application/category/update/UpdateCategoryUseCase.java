package org.fullcycle.catalog.admin.application.category.update;

import org.fullcycle.catalog.admin.application.base.UseCase;
import org.fullcycle.catalog.admin.domain.category.Category;
import org.fullcycle.catalog.admin.domain.category.CategoryGateway;
import org.fullcycle.catalog.admin.domain.category.CategoryID;
import org.fullcycle.catalog.admin.domain.exception.NotFoundException;

import java.util.function.Supplier;

public class UpdateCategoryUseCase extends UseCase<UpdateCategoryCommand, UpdateCategoryOutput> {

    private final CategoryGateway categoryGateway;

    public UpdateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public UpdateCategoryOutput execute(final UpdateCategoryCommand command) {
        final var categoryId = CategoryID.of(command.id());
        final var existingCategory = categoryGateway.findById(categoryId)
            .orElseThrow(notFoundException(categoryId));
        final var updatedCategory = existingCategory.update(
            command.name(),
            command.description(),
            command.isActive()
        );
        final var persistedCategory = categoryGateway.update(updatedCategory);
        return UpdateCategoryOutput.from(persistedCategory);
    }

    private static Supplier<NotFoundException> notFoundException(final CategoryID id) {
        return () -> NotFoundException.with(Category.class, id);
    }
}
