package org.fullcycle.catalog.admin.domain.exception;

public class NoStackTraceException extends RuntimeException {

    public NoStackTraceException(final String message) {
        super(message);
    }

    public NoStackTraceException(final String message, final Throwable cause) {
        super(message, cause, true, false);
    }
}
