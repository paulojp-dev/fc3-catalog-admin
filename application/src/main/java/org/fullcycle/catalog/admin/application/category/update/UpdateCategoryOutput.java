package org.fullcycle.catalog.admin.application.category.update;

import org.fullcycle.catalog.admin.domain.category.Category;
import org.fullcycle.catalog.admin.domain.category.CategoryID;

public record UpdateCategoryOutput(
        CategoryID id
) {
    public static UpdateCategoryOutput from(Category category) {
        return new UpdateCategoryOutput(category.getId());
    }
}
