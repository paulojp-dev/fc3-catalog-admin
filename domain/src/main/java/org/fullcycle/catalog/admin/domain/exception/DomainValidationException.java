package org.fullcycle.catalog.admin.domain.exception;

import org.fullcycle.catalog.admin.domain.validation.Error;

import java.util.List;

public class DomainValidationException extends DomainException {

    private final List<Error> errors;

    public DomainValidationException(final List<Error> errors) {
        super("domain validation error");
        this.errors = errors;
    }

    public static DomainValidationException with(Error error) {
        return new DomainValidationException(List.of(error));
    }

    public static DomainValidationException with(final List<Error> errors) {
        return new DomainValidationException(errors);
    }

    public List<Error> getErrors() {
        return errors;
    }
}
