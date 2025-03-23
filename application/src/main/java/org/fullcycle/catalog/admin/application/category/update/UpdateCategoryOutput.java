package org.fullcycle.catalog.admin.application.category.update;

import org.fullcycle.catalog.admin.domain.base.ID;
import org.fullcycle.catalog.admin.domain.category.Category;

public record UpdateCategoryOutput(
    ID id
) {

    public static UpdateCategoryOutput from(Category category) {
        return new UpdateCategoryOutput(category.getId());
    }
}
