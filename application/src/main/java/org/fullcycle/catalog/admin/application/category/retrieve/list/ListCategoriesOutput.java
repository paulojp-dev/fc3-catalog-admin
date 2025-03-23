package org.fullcycle.catalog.admin.application.category.retrieve.list;

import org.fullcycle.catalog.admin.domain.category.Category;

public record ListCategoriesOutput(
    String id,
    String name,
    String description,
    Boolean isActive
) {

    public static ListCategoriesOutput from(final Category category) {
        return new ListCategoriesOutput(
            category.getId().getValue(),
            category.getName(),
            category.getDescription(),
            category.isActive()
        );
    }
}
