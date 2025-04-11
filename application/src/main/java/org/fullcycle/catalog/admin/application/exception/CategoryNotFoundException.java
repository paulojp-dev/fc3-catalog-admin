package org.fullcycle.catalog.admin.application.exception;

public class CategoryNotFoundException extends NotFoundException {

    public CategoryNotFoundException(final String message) {
        super(message);
    }

    public static CategoryNotFoundException byId(final String id) {
        final var message = String.format("Category not found from ID '%s'", id);
        return new CategoryNotFoundException(message);
    }
}
