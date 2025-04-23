package org.fullcycle.catalog.admin.domain.exception;

import org.fullcycle.catalog.admin.domain.base.AggregateRoot;
import org.fullcycle.catalog.admin.domain.base.Identifier;

public class NotFoundException extends DomainException {

    public NotFoundException(String message) {
        super(message);
    }

    public static NotFoundException with(
        final Class<? extends AggregateRoot<?>> aggregate,
        final Identifier id
    ) {
        final var message = "%s with ID %s was not found".formatted(aggregate.getSimpleName(), id.getValue());
        return new NotFoundException(message);
    }
}
