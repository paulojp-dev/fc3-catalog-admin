package org.fullcycle.catalog.admin.infrastructure.api.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.fullcycle.catalog.admin.application.category.retrieve.get.GetCategoryByIdOutput;

import java.time.Instant;

public record CategoryApiOutput(
    @JsonProperty("id") String id,
    @JsonProperty("name") String name,
    @JsonProperty("description") String description,
    @JsonProperty("is_active") Boolean isActive,
    @JsonProperty("created_at") Instant createdAt,
    @JsonProperty("updated_at") Instant updatedAt,
    @JsonProperty("deleted_at") Instant deletedAt
) {

    public static CategoryApiOutput from(GetCategoryByIdOutput category) {
        return new CategoryApiOutput(
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
