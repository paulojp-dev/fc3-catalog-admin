package org.fullcycle.catalog.admin.infrastructure.api.output;

import org.fullcycle.catalog.admin.application.category.create.CreateCategoryOutput;

public record CreateCategoryApiOutput(String id) {

    public static CreateCategoryApiOutput from(CreateCategoryOutput createCategoryOutput) {
        return new CreateCategoryApiOutput(createCategoryOutput.id());
    }
}
