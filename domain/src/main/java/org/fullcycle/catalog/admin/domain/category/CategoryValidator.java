package org.fullcycle.catalog.admin.domain.category;

import org.fullcycle.catalog.admin.domain.validation.Validator;

public class CategoryValidator extends Validator {

    private final Category category;

    public CategoryValidator(final Category category) {
        this.category = category;
    }

    @Override
    public void validate() {
        setRules("name")
                .required()
                .apply(category.getName());
        throwIfErrors();
    }
}
