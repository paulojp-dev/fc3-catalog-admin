package org.fullcycle.catalog.admin.application.category.create;

import org.fullcycle.catalog.admin.domain.base.ID;
import org.fullcycle.catalog.admin.domain.category.Category;

public record CreateCategoryOutput(
    ID id
) {

    public static CreateCategoryOutput from(final Category category) {
        return new CreateCategoryOutput(category.getId());
    }
}
