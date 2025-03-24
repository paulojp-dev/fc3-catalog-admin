package org.fullcycle.catalog.admin.application.category.delete;

import org.fullcycle.catalog.admin.application.base.NoOutputUseCase;
import org.fullcycle.catalog.admin.domain.category.CategoryGateway;
import org.fullcycle.catalog.admin.domain.category.CategoryID;

public class DeleteCategoryUseCase extends NoOutputUseCase<String> {

    private final CategoryGateway categoryGateway;

    public DeleteCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public void execute(final String id) {
        final var result = categoryGateway.findById(CategoryID.of(id));
        result.ifPresent(category -> categoryGateway.deleteById(category.getId()));
    }
}
