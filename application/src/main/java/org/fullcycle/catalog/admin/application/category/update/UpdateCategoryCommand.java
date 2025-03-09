package org.fullcycle.catalog.admin.application.category.update;

import org.fullcycle.catalog.admin.domain.category.CategoryID;

public record UpdateCategoryCommand(
        CategoryID id,
        String name,
        String description,
        Boolean isActive
) {
    public static UpdateCategoryCommand of(
            final CategoryID id,
            final String name,
            final String description,
            final Boolean isActive
    ) {
        return new UpdateCategoryCommand(id, name, description, isActive);
    }
}
