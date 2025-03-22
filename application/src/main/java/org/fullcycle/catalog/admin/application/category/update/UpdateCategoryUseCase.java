package org.fullcycle.catalog.admin.application.category.update;

import org.fullcycle.catalog.admin.application.base.UseCase;
import org.fullcycle.catalog.admin.application.exception.CategoryNotFoundException;
import org.fullcycle.catalog.admin.domain.category.CategoryGateway;
import org.fullcycle.catalog.admin.domain.base.ID;

import java.util.function.Supplier;

public class UpdateCategoryUseCase extends UseCase<UpdateCategoryCommand, UpdateCategoryOutput> {

    private final CategoryGateway categoryGateway;

    public UpdateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public UpdateCategoryOutput execute(final UpdateCategoryCommand command) {
        final var existingCategory = categoryGateway.findById(ID.of(command.id()))
                .orElseThrow(notFoundException(command.id()));
        final var updatedCategory = existingCategory.update(
                command.name(),
                command.description(),
                command.isActive()
        );
        final var persistedCategory = categoryGateway.update(updatedCategory);
        return UpdateCategoryOutput.from(persistedCategory);
    }

    private static Supplier<CategoryNotFoundException> notFoundException(final String id) {
        return () -> CategoryNotFoundException.byId(id);
    }
}
