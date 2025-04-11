package org.fullcycle.catalog.admin.infrastructure.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.fullcycle.catalog.admin.application.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class NotFoundExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse handleValidationExceptions(NotFoundException e, HttpServletRequest request) {
        return new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            HttpStatus.NOT_FOUND.getReasonPhrase(),
            e.getMessage(),
            request.getRequestURI()
        );
    }

    public record ErrorResponse(
        int status,
        String error,
        String message,
        String path
    ) {

    }
}
