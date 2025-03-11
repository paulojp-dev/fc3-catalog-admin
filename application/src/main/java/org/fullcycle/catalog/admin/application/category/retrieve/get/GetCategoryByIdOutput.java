package org.fullcycle.catalog.admin.application.category.retrieve.get;

import org.fullcycle.catalog.admin.domain.category.Category;

public record GetCategoryByIdOutput(
        String id,
        String name,
        String description,
        Boolean isActive
) {
    public static GetCategoryByIdOutput from(final Category category) {
        return new GetCategoryByIdOutput(
                category.getId().getValue(),
                category.getName(),
                category.getDescription(),
                category.isActive()
        );
    }
}
