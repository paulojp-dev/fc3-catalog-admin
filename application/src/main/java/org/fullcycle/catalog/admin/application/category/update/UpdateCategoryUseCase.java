package org.fullcycle.catalog.admin.application.category.update;

import org.fullcycle.catalog.admin.application.base.UseCase;
import org.fullcycle.catalog.admin.application.exception.ResourceNotFoundException;
import org.fullcycle.catalog.admin.domain.category.CategoryGateway;

public class UpdateCategoryUseCase extends UseCase<UpdateCategoryCommand, UpdateCategoryOutput> {

    private final CategoryGateway categoryGateway;

    public UpdateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public UpdateCategoryOutput execute(UpdateCategoryCommand command) {
        final var existingCategory = categoryGateway.findById(command.id())
                .orElseThrow(() -> ResourceNotFoundException.byId("Category", command.id().getValue()));
        final var updatedCategory = existingCategory.update(command.name(), command.description());
        final var persistedCategory = categoryGateway.update(updatedCategory);
        return UpdateCategoryOutput.from(persistedCategory);
    }
}
