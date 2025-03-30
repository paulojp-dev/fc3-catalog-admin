package org.fullcycle.catalog.admin.infrastructure.exception.handler;

import org.fullcycle.catalog.admin.domain.exception.DomainValidationException;
import org.fullcycle.catalog.admin.domain.validation.Error;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DomainValidationExceptionHandlerTest {

    private final DomainValidationExceptionHandler handler = new DomainValidationExceptionHandler();

    @Test
    public void givenADomainValidationException_whenHandled_shouldReturnErrorsMap() throws Exception {
        final var exception = new DomainValidationException(List.of(new Error("name", "Name is invalid")));
        final var response = handler.handleValidationExceptions(exception);
        Assertions.assertEquals("Name is invalid", response.get("name"));
    }
}
