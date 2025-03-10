package org.fullcycle.catalog.admin.application.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(final String message) {
        super(message);
    }

    public static ResourceNotFoundException byId(final String id) {
        return byId("Resource", id);
    }

    public static ResourceNotFoundException byId(final String resourceName, final String id) {
        final var message = String.format("%s not found from ID '%s'", resourceName, id);
        return new ResourceNotFoundException(message);
    }
}
