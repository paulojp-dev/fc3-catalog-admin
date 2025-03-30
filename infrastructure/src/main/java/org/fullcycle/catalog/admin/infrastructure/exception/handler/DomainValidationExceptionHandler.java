package org.fullcycle.catalog.admin.infrastructure.exception.handler;

import org.fullcycle.catalog.admin.domain.exception.DomainValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class DomainValidationExceptionHandler {

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(DomainValidationException.class)
    public Map<String, String> handleValidationExceptions(DomainValidationException e) {
        Map<String, String> errors = new HashMap<>();
        e.getErrors().forEach(error ->
            errors.put(error.field(), error.message())
        );
        return errors;
    }
}
