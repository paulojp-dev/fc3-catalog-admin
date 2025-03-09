package org.fullcycle.catalog.admin.application.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public static ResourceNotFoundException byId(String id) {
        return byId("Resource", id);
    }

    public static ResourceNotFoundException byId(String resourceName, String id) {
        final var message = String.format("%s not found from ID '%s'", resourceName, id);
        return new ResourceNotFoundException(message);
    }
}
