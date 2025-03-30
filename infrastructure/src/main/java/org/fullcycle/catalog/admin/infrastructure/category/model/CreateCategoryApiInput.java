package org.fullcycle.catalog.admin.infrastructure.category.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCategoryApiInput(
    @JsonProperty("name")
    @NotBlank(message = "Name cannot be null")
    @Size(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
    String name,

    @JsonProperty("description")
    @Size(max = 500, message = "Description must be at most 500 characters")
    String description,

    @JsonProperty("is_active")
    @NotNull(message = "Active flag cannot be null")
    Boolean active
) {

}
