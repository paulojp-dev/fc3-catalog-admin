package org.fullcycle.catalog.admin.domain.exception;

import org.fullcycle.catalog.admin.domain.validation.Error;
import java.util.List;

public class DomainException extends NoStackTraceException {

    private final List<Error> errors;

    public DomainException(final String message, final List<Error> errors) {
        super(message);
        this.errors = errors;
    }

    public static DomainException with(Error error) {
        return new DomainException("", List.of(error));
    }

    public static DomainException with(final List<Error> errors) {
        return new DomainException("", errors);
    }

    public List<Error> getErrors() {
        return errors;
    }
}
