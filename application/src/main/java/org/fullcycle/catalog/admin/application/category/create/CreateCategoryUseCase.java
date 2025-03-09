package org.fullcycle.catalog.admin.application.category.create;

import org.fullcycle.catalog.admin.application.base.UseCase;
import org.fullcycle.catalog.admin.domain.category.Category;
import org.fullcycle.catalog.admin.domain.category.CategoryGateway;

public class CreateCategoryUseCase extends UseCase<CreateCategoryCommand, CreateCategoryOutput> {

    private final CategoryGateway categoryGateway;

    public CreateCategoryUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public CreateCategoryOutput execute(final CreateCategoryCommand command) {
        final var category = Category.of(command.name(), command.description(), command.isActive());
        return CreateCategoryOutput.from(categoryGateway.create(category));
    }
}
