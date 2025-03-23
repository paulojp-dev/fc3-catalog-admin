package org.fullcycle.catalog.admin.domain.category;

import org.fullcycle.catalog.admin.domain.validation.Validation;

public class CategoryValidation extends Validation {

    private final Category category;

    public CategoryValidation(final Category category) {
        this.category = category;
    }

    @Override
    public void validate() {
        setRules("name")
            .required()
            .min(3)
            .max(255)
            .apply(category.getName());
        setRules("description")
            .max(255)
            .apply(category.getDescription());
        throwIfErrors();
    }
}
