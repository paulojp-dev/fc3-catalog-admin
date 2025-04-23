package org.fullcycle.catalog.admin.domain.exception;

public class DomainException extends NoStackTraceException {

    public DomainException(final String message) {
        super(message);
    }
}
