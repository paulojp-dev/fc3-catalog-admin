package org.fullcycle.catalog.admin.domain.category;

import org.fullcycle.catalog.admin.domain.validation.Error;
import org.fullcycle.catalog.admin.domain.validation.ValidationHandler;
import org.fullcycle.catalog.admin.domain.validation.Validator;

public class CategoryValidator extends Validator {

    private final Category category;

    public CategoryValidator(final Category category, final ValidationHandler handler) {
        super(handler);
        this.category = category;
    }

    @Override
    public void validate() {
        if (this.category.getName() == null || this.category.getName().isEmpty()) {
            this.handler().append(new Error("'name' should not be null"));
        }
    }
}
