package org.fullcycle.catalog.admin.application.category.create;

public record CreateCategoryCommand(
        String name,
        String description,
        Boolean isActive
) {
    public static CreateCategoryCommand of(
            final String name,
            final String description,
            final Boolean isActive
    ) {
        return new CreateCategoryCommand(name, description, isActive);
    }
}
