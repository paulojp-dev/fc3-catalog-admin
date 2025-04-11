package org.fullcycle.catalog.admin.application.category.retrieve.get;

import org.fullcycle.catalog.admin.domain.category.Category;

import java.time.Instant;

public record GetCategoryByIdOutput(
    String id,
    String name,
    String description,
    Boolean isActive,
    Instant createdAt,
    Instant updatedAt,
    Instant deletedAt
) {

    public static GetCategoryByIdOutput from(final Category category) {
        return new GetCategoryByIdOutput(
            category.getId().getValue(),
            category.getName(),
            category.getDescription(),
            category.isActive(),
            category.getCreatedAt(),
            category.getUpdatedAt(),
            category.getDeletedAt().orElse(null)
        );
    }
}
