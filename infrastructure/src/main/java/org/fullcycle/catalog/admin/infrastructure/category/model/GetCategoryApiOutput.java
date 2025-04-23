package org.fullcycle.catalog.admin.infrastructure.category.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.fullcycle.catalog.admin.application.category.retrieve.get.GetCategoryByIdOutput;

import java.time.Instant;

public record GetCategoryApiOutput(
    @JsonProperty("id") String id,
    @JsonProperty("name") String name,
    @JsonProperty("description") String description,
    @JsonProperty("is_active") Boolean isActive,
    @JsonProperty("created_at") Instant createdAt,
    @JsonProperty("updated_at") Instant updatedAt,
    @JsonProperty("deleted_at") Instant deletedAt
) {

    public static GetCategoryApiOutput from(GetCategoryByIdOutput category) {
        return new GetCategoryApiOutput(
            category.id(),
            category.name(),
            category.description(),
            category.isActive(),
            category.createdAt(),
            category.updatedAt(),
            category.deletedAt()
        );
    }
}
